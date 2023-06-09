package com.binance.crypto.bot.config.security.listener;

import com.binance.crypto.bot.api.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFormFailureHandler implements AuthenticationFailureHandler {

    private static final String USERNAME_PARAMETER = "username";

    private final UserService userService;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) {
        final String lowercaseUsername = StringUtils.lowerCase(request.getParameter(USERNAME_PARAMETER));
        userService.processAuthenticationFailure(lowercaseUsername);
        throw new UnsupportedOperationException(String.format("Invalid credentials for user: '%s'", lowercaseUsername));
    }
}