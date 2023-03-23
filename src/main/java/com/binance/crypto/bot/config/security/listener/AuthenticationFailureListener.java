package com.binance.crypto.bot.config.security.listener;

import com.binance.crypto.bot.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent event) {
        final String lowercaseUsername = StringUtils.lowerCase(event.getAuthentication().getName());
        userService.processAuthenticationFailure(lowercaseUsername);
    }
}
