package com.binance.crypto.bot.config.twofa.service;

import com.binance.crypto.bot.api.user.entity.User;
import com.binance.crypto.bot.api.user.service.UserService;
import com.binance.crypto.bot.config.twofa.data.QrCode;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleAuthenticatorService {

    private final GoogleAuthenticator googleAuthenticator;
    private final UserService userService;

    @Value("${application.name}")
    private String applicationName;

    @Transactional(readOnly = true)
    public boolean isEnabled(final String username) {
        final String lowercaseUsername = StringUtils.lowerCase(username);
        final User user = userService.findOneByUsername(lowercaseUsername)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The username %s doesn't exist", lowercaseUsername)));
        Validate.isTrue(user.getActive(), "User is not active");

        return Boolean.TRUE.equals(user.getQrCodeEnabled());
    }

    @Transactional
    public QrCode generateQRCode(final String username) {
        final String lowercaseUsername = StringUtils.lowerCase(username);

        final User user = validateIsTwoFactorQrAuthEnabled(lowercaseUsername);

        final QrCode qrCode;
        if (Boolean.FALSE.equals(user.getQrCodeCreated())) {
            log.info(String.format("Generating new QR code for user '%s'", lowercaseUsername));

            final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(lowercaseUsername);
            final String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(applicationName, lowercaseUsername, key);

            userService.save(user);

            qrCode = QrCode.builder().otpAuthURL(otpAuthURL).generated(true).build();
        } else {
            qrCode = QrCode.builder().generated(false).build();
        }

        return qrCode;
    }

    private User validateIsTwoFactorQrAuthEnabled(final String lowercaseUsername) {
        final User user = userService.findOneByUsername(lowercaseUsername)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The username %s doesn't exist", lowercaseUsername)));
        Validate.isTrue(BooleanUtils.isTrue(user.getQrCodeEnabled()), "QR code two factor authentication is not enabled for user '%s'", lowercaseUsername);
        Validate.isTrue(user.getActive(), "User is not active");

        return user;
    }

    @Transactional(readOnly = true)
    public boolean verifyCode(final String username, final String validationCode) {
        try {
            Validate.notBlank(username, "Username is undefined");
            Validate.notNull(validationCode, "Validation code is undefined");
            final String lowercaseUsername = StringUtils.lowerCase(username);
            validateIsTwoFactorQrAuthEnabled(lowercaseUsername);

            return googleAuthenticator.authorizeUser(lowercaseUsername, Integer.parseInt(validationCode));
        } catch (final Exception e) {
            log.error("Failed to verify QR code", e);
            return false;
        }
    }
}
