package com.binance.crypto.bot.api.userpasswordhistories.service;

import com.binance.crypto.bot.api.userpasswordhistories.entity.UserPasswordHistory;
import com.binance.crypto.bot.api.userpasswordhistories.repository.UserPasswordHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPasswordHistoryService {

	private final UserPasswordHistoryRepository userPasswordHistoryRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public boolean validatePassword(final long userId, final String newPassword, final int userPasswordHistoryLastCount) {
		Validate.notBlank(newPassword, "Password is undefined");
		final List<UserPasswordHistory> userPasswordHistories =
			userPasswordHistoryRepository.findByUserIdOrderByCreatedDesc(userId, PageRequest.of(0, userPasswordHistoryLastCount));
		return userPasswordHistories.stream().noneMatch(passwordHistory -> {
			final String oldPassword = Validate.notBlank(passwordHistory.getPassword(), "Password is undefined");
			return passwordEncoder.matches(newPassword, oldPassword);
		});
	}

	@Transactional
	public void saveUserPassword(final long userId, final String encodedPassword) {
		Validate.notBlank(encodedPassword, "Encoded password is undefined");

		final UserPasswordHistory passwordHistory = new UserPasswordHistory();
		passwordHistory.setUserId(userId);
		passwordHistory.setPassword(encodedPassword);
		userPasswordHistoryRepository.save(passwordHistory);
	}
}
