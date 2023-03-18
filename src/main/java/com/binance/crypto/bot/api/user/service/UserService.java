package com.binance.crypto.bot.api.user.service;


import com.binance.crypto.bot.api.actionlogtypes.entity.ActionLog;
import com.binance.crypto.bot.api.common.passwordvalidator.PasswordValidatorErrorMessage;
import com.binance.crypto.bot.api.common.passwordvalidator.PasswordValidatorException;
import com.binance.crypto.bot.api.common.passwordvalidator.PasswordValidatorResult;
import com.binance.crypto.bot.api.user.data.UserData;
import com.binance.crypto.bot.api.user.entity.User;
import com.binance.crypto.bot.api.user.exception.UserAlreadyExistsException;
import com.binance.crypto.bot.api.user.exception.UserNotFoundException;
import com.binance.crypto.bot.api.user.repository.UserRepository;
import com.binance.crypto.bot.api.useractionlog.service.UserActionLogService;
import com.binance.crypto.bot.api.userpasswordhistories.service.UserPasswordHistoryService;
import com.binance.crypto.bot.utils.DateUtils;
import com.binance.crypto.bot.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.modelmapper.ModelMapper;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;
import org.passay.WhitespaceRule;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 40;
	private static final int LOGIN_MAX_ATTEMPT = 6;

	private final UserRepository userRepository;
	private final UserActionLogService userActionLogService;
	private final UserPasswordHistoryService userPasswordHistoryService;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Optional<User> findOneByUsername(final String username) {
		return userRepository.findOneByUsername(username);
	}

	@Transactional(readOnly = true)
	public User loadOneByUsernameAndActiveIsTrue(final String username) {
		return userRepository.findOneByUsernameAndActiveIsTrue(username)
			.orElseThrow(() -> new EntityNotFoundException(String.format("User with username '%s' was not found", username)));
	}

	@Transactional(readOnly = true)
	public User loadById(final long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("user with ID '%s' was not found", userId)));
	}

	@Transactional(rollbackFor = Exception.class)
	public UserData create(final long requestingUserId, final UserData userData){
		final User userToCreate = createUserEntityFromData(userData);

		final String encodedPassword = passwordEncoder.encode(userData.getRawPassword());
		final User user = save(userToCreate, encodedPassword, userData.getRawPassword());
		Validate.notNull(user, "created user is undefined");
		userPasswordHistoryService.saveUserPassword(user.getId(), encodedPassword);

		return modelMapper.map(loadById(requestingUserId), UserData.class);
	}

	private User createUserEntityFromData(final UserData userData) {
		Validate.notNull(userData, "userData is undefined");
		final User user = new User();
		user.setName(userData.getName());
		user.setUsername(userData.getUsername());
		user.setActive(userData.getActive());

		user.setLastLogin(new Date());
		user.setAccountNonLocked(userData.getAccountNonLocked());
		user.setFailedAttempt(0);
		user.setPasswordExpiry(DateUtils.minusDays(new Date(), 1));
		user.setLastRequest(new Date());

		return user;
	}

	@Transactional(rollbackFor = Exception.class)
	public User save(final User user, final String password, final String rawPassword) {
		Validate.notNull(user, "user is undefined");
		Validate.notBlank(user.getUsername(), "username is blank");
		Validate.notBlank(user.getName(), "name is blank");
		Validate.notBlank(password, "password is blank");
		validatePassword(rawPassword);
		Validate.isTrue(user.getQrCodeEnabled(), "User must have two-factor authentication enabled!");

		if (userRepository.existsByUsername(user.getUsername())) {
			throw new UserAlreadyExistsException(user.getUsername());
		}

		stripEntityHtml(user);
		user.setPassword(password);
		userRepository.save(user);

		Validate.notNull(user.getId(), "id is undefined for created user '%s'", user);
		return user;
	}

	private void validatePassword(final String rawPassword) {
		final PasswordValidatorResult passwordValidatorResult = validatePasswordComplexity(rawPassword);
		if (!passwordValidatorResult.isValid()) {
			throw new PasswordValidatorException(passwordValidatorResult.getErrorMessage());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public User save(final long userId, final User user, final String password, final String rawPassword) {
		Validate.notNull(user, "user is undefined");
		Validate.notBlank(user.getUsername(), "username is blank");
		Validate.notBlank(user.getName(), "name is blank");

		final User existingUser =
			userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("User not found by id: '%d'", userId)));
		Validate.isTrue(existingUser.getQrCodeEnabled(), "User must have two-factor authentication enabled!");

		if (password != null) {
			validatePassword(rawPassword);
			existingUser.setPassword(password);
		}

		stripEntityHtml(user);

		existingUser.setName(user.getName());
		existingUser.setActive(user.getActive());
		existingUser.setAccountNonLocked(user.getAccountNonLocked());

		userRepository.save(existingUser);

		return existingUser;
	}

	@Transactional(rollbackFor = Exception.class)
	public User save(final User user) {
		stripEntityHtml(user);
		return userRepository.save(user);
	}

	private void stripEntityHtml(final User user) {
		final String username = SecurityUtils.stripHtml(user.getUsername());

		user.setUsername(StringUtils.lowerCase(username));
		user.setName(SecurityUtils.stripHtml(user.getName()));
		user.setPassword(SecurityUtils.stripHtml(user.getPassword()));
	}

	public PasswordValidatorResult validatePasswordComplexity(final String rawPassword) {
		Validate.notBlank(rawPassword, "rawPassword is undefined");
		final PasswordData passwordData = new PasswordData(rawPassword);
		final PasswordValidator passwordValidator = new PasswordValidator(createPasswordRules());
		final RuleResult ruleResult = passwordValidator.validate(passwordData);
		final String errorMessage = getErrorMessage(ruleResult.getDetails()).orElse(null);

		return PasswordValidatorResult.builder().isValid(ruleResult.isValid()).errorMessage(errorMessage).build();
	}

	private Optional<String> getErrorMessage(final List<RuleResultDetail> details) {
		final Optional<String> errorMessage;
		if (!details.isEmpty()) {
			final String errorCode = details.get(0).getErrorCode();
			errorMessage = PasswordValidatorErrorMessage.getById(errorCode).map(PasswordValidatorErrorMessage::getMessage);
		} else {
			errorMessage = Optional.empty();
		}

		return errorMessage;
	}

	private List<Rule> createPasswordRules() {
		final List<Rule> passwordRules = new ArrayList<>();
		passwordRules.add(new LengthRule(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH));
		passwordRules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
		passwordRules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
		passwordRules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
		passwordRules.add(new CharacterRule(EnglishCharacterData.Special, 1));
		passwordRules.add(new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false));
		passwordRules.add(new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false));
		passwordRules.add(new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false));
		passwordRules.add(new WhitespaceRule());

		return passwordRules;
	}

	public void increaseFailedAttempts(final User user) {
		Validate.notNull(user, "User is undefined");
		int newFailAttempts = user.getFailedAttempt() + 1;
		user.setFailedAttempt(newFailAttempts);

		userRepository.save(user);
	}

	@Transactional
	public void resetUserLoginDataAfterSuccessfulAuth(final long userId, final String ip) {
		final User user = loadById(userId);
		user.setLastLogin(new Date());
		user.setFailedAttempt(0);
		user.setLastIp(ip);
		user.setQrCodeCreated(true);

		userRepository.saveAndFlush(user);
	}

	public void lockUser(final User user) {
		Validate.notNull(user, "User is undefined");
		user.setAccountNonLocked(false);

		userRepository.save(user);
	}

	@Transactional
	public void setInactive(final long userId) {
		final User user = loadById(userId);
		user.setActive(false);

		userRepository.saveAndFlush(user);
	}

	@Transactional(rollbackFor = Exception.class)
	public void processAuthenticationFailure(final String lowercaseUsername) {
		final User user = findOneByUsername(lowercaseUsername)
			.orElseThrow(() -> new EntityNotFoundException(String.format("The username %s doesn't exist", lowercaseUsername)));

		if (Boolean.TRUE.equals(Objects.nonNull(user) && user.getActive()) && Boolean.TRUE.equals(user.getAccountNonLocked())) {
			final long userId = user.getId();
			if (user.getFailedAttempt() < LOGIN_MAX_ATTEMPT) {
				final String message =
					String.format("User with id: '%d' entered incorrect password. Login attempt increased to: '%d'", userId, user.getFailedAttempt() + 1);
				userActionLogService.logUserAction(userId, ActionLog.Type.USER, message);
				increaseFailedAttempts(user);
			} else {
				final String message = String.format("User with id: '%d' locked.", userId);
				userActionLogService.logUserAction(userId, ActionLog.Type.USER, message);
				lockUser(user);
			}
		}
	}
}
