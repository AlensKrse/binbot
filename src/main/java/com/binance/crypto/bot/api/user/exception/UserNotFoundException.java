package com.binance.crypto.bot.api.user.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(final String message) {
        super(message);
    }
}
