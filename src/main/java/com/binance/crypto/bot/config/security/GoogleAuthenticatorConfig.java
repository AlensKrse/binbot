package com.binance.crypto.bot.config.security;

import com.binance.crypto.bot.config.twofa.repository.QrCodeCredentialRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GoogleAuthenticatorConfig {

    private final QrCodeCredentialRepository qrCodeCredentialRepository;

    @Bean
    public GoogleAuthenticator googleAuthenticator() {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setCredentialRepository(qrCodeCredentialRepository);
        return googleAuthenticator;
    }
}