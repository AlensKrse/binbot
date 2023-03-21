package com.binance.crypto.bot.config.security.jwt;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsernamePasswordAuthenticationRequest {

    String username;
    String password;
}
