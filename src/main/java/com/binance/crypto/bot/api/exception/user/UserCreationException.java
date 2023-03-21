package com.binance.crypto.bot.api.exception.user;


import com.binance.crypto.bot.api.common.response.MessageResourceException;

import java.util.Collections;
import java.util.Map;

public class UserCreationException extends MessageResourceException {

    private final String errorMessage;

    public UserCreationException(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessageResourceId() {
        return "bot.errors.users.creation";
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
