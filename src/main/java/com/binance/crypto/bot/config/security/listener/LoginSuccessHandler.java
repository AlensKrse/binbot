package com.binance.crypto.bot.config.security.listener;

import com.binance.crypto.bot.api.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException, ServletException {
        Validate.notNull(request, "Request is undefined");
        Validate.notNull(authentication, "Authentication is undefined");

        final User user = (User) authentication.getPrincipal();
        final com.binance.crypto.bot.api.user.entity.User appUser = userService.loadOneByUsernameAndActiveIsTrue(user.getUsername());
        userService.resetUserLoginDataAfterSuccessfulAuth(appUser.getId(), request.getRemoteAddr());

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
