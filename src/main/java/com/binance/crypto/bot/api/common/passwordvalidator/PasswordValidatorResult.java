package com.binance.crypto.bot.api.common.passwordvalidator;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordValidatorResult {

	boolean isValid;
	String errorMessage;

	public static PasswordValidatorResult success() {
		return PasswordValidatorResult.builder().isValid(true).build();
	}

	public static PasswordValidatorResult failure(final String errorMessage) {
		return PasswordValidatorResult.builder().isValid(false).errorMessage(errorMessage).build();
	}
}
