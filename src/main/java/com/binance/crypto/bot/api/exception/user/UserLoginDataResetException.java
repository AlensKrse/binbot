package com.binance.crypto.bot.api.exception.user;


import com.binance.crypto.bot.api.common.response.MessageResourceException;

import java.util.Collections;
import java.util.Map;

public class UserLoginDataResetException extends MessageResourceException {

    private final String errorMessage;

    public UserLoginDataResetException(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessageResourceId() {
        return "bot.errors.users.login-data-reset";
    }

    @Override
    public Map<String, String> getMessageResourceParameters() {
        return Collections.emptyMap();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
