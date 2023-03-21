package com.binance.crypto.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class BinanceCryptoBotApplicationTest extends BaseIntegrationTest {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

    @Test
    void testPasswordEncoder() {
        final String rawPassword = "admin";
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        final String encodedPassword = encoder.encode(rawPassword);
        Assertions.assertTrue(encoder.matches(rawPassword, encodedPassword));

        final String rawPassword2 = "user";
        final BCryptPasswordEncoder encoder2 = new BCryptPasswordEncoder();

        final String encodedPassword2 = encoder2.encode(rawPassword2);
        Assertions.assertTrue(encoder2.matches(rawPassword2, encodedPassword2));
    }

}