package com.binance.crypto.bot.api.common.auth.service;

import com.binance.crypto.bot.api.common.auth.data.AuthorizationLoginRequest;
import com.binance.crypto.bot.api.common.auth.data.AuthorizationLoginResponse;
import com.binance.crypto.bot.api.user.entity.User;
import com.binance.crypto.bot.api.user.service.UserService;
import com.binance.crypto.bot.config.jwt.JwtUtils;
import com.binance.crypto.bot.config.twofa.data.QrCode;
import com.binance.crypto.bot.config.twofa.service.GoogleAuthenticatorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    private static final String BEARER = "Bearer";

    AuthenticationManager authenticationManager;
    GoogleAuthenticatorService googleAuthenticatorService;
    JwtUtils jwtUtils;
    UserService userService;

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

    public AuthorizationLoginResponse authenticateUser(final AuthorizationLoginRequest loginRequest) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String username = userDetails.getUsername();
        final User user = userService.loadOneByUsernameAndActiveIsTrue(username);
        if (googleAuthenticatorService.isEnabled(username)) {
            final String code = loginRequest.getCode();
            if (StringUtils.isBlank(code)) {
                final QrCode qrCode = googleAuthenticatorService.generateQRCode(username);
                return AuthorizationLoginResponse.builder().success(false).qrCodeGenerated(qrCode.isGenerated()).otpAuthURL(qrCode.getOtpAuthURL()).username(user.getUsername()).build();
            } else {
                final boolean codeValid = googleAuthenticatorService.verifyCode(username, code);
                if (codeValid) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    final String token = jwtUtils.generateJwtToken(authentication);

                    return AuthorizationLoginResponse.builder().success(true).id(user.getId()).username(user.getUsername()).roleId(user.getRoleId()).token(token).type(BEARER).build();
                } else {
                    userService.increaseFailedAttempts(user);
                    throw new BadCredentialsException("Invalid MFA code for user: " + username);
                }
            }
        } else {
            throw new BadCredentialsException("Two-factor authentication is not enabled for user: " + username);
        }
    }
}
