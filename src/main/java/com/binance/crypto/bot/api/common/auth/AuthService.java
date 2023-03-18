package com.binance.crypto.bot.api.common.auth;

import com.binance.crypto.bot.api.user.entity.User;
import com.binance.crypto.bot.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserService userService;

	@Transactional(readOnly = true)
	public long getUserId() {
		final User user = getCurrentUser();
		return Validate.notNull(user.getId(), "id is undefined for user: %s", user);
	}

	private User getCurrentUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Validate.notNull(authentication, "authentication is undefined");

		final String username = Validate.notBlank(authentication.getName(), "name is blank for authentication: %s", authentication);
		final User user = userService.loadOneByUsernameAndActiveIsTrue(username);
		return Validate.notNull(user, "could not find active user with username '%s'", username);
	}
}
