package com.binance.crypto.bot.api.common.passwordvalidator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum PasswordValidatorErrorMessage {

		HISTORY_VIOLATION("HISTORY_VIOLATION", "Password matches one of previous passwords."),
		ILLEGAL_WORD("ILLEGAL_WORD", "Password contains the dictionary word."),
		ILLEGAL_WORD_REVERSED("ILLEGAL_WORD_REVERSED", "Password contains the reversed dictionary word."),
		ILLEGAL_DIGEST_WORD("ILLEGAL_DIGEST_WORD", "Password contains a dictionary word."),
		ILLEGAL_DIGEST_WORD_REVERSED("ILLEGAL_DIGEST_WORD_REVERSED", "Password contains a reversed dictionary word."),
		ILLEGAL_MATCH("ILLEGAL_MATCH", "Password matches the illegal pattern."),
		ALLOWED_MATCH("ALLOWED_MATCH", "Password must match pattern."),
		ILLEGAL_CHAR("ILLEGAL_CHAR", "Password contains the illegal character."),
		ALLOWED_CHAR("ALLOWED_CHAR", "Password contains the illegal character."),
		ILLEGAL_QWERTY_SEQUENCE("ILLEGAL_QWERTY_SEQUENCE", "Password contains the illegal QWERTY sequence."),
		ILLEGAL_ALPHABETICAL_SEQUENCE("ILLEGAL_ALPHABETICAL_SEQUENCE", "Password contains the illegal alphabetical sequence."),
		ILLEGAL_NUMERICAL_SEQUENCE("ILLEGAL_NUMERICAL_SEQUENCE", "Password contains the illegal numerical sequence."),
		ILLEGAL_USERNAME("ILLEGAL_USERNAME", "Password contains the user id."),
		ILLEGAL_USERNAME_REVERSED("ILLEGAL_USERNAME_REVERSED", "Password contains the user id in reverse."),
		ILLEGAL_WHITESPACE("ILLEGAL_WHITESPACE", "Password contains a whitespace character."),
		ILLEGAL_NUMBER_RANGE("ILLEGAL_NUMBER_RANGE", "Password contains the illegal number ."),
		ILLEGAL_REPEATED_CHARS("ILLEGAL_REPEATED_CHARS", "Password contains sequences of repeated characters."),
		INSUFFICIENT_UPPERCASE("INSUFFICIENT_UPPERCASE", "Password must contain uppercase character."),
		INSUFFICIENT_LOWERCASE("INSUFFICIENT_LOWERCASE", "Password must contain lowercase character."),
		INSUFFICIENT_ALPHABETICAL("INSUFFICIENT_ALPHABETICAL", "Password must contain alphabetical character."),
		INSUFFICIENT_DIGIT("INSUFFICIENT_DIGIT", "Password must contain digit character."),
		INSUFFICIENT_SPECIAL("INSUFFICIENT_SPECIAL", "Password must contain special character."),
		INSUFFICIENT_CHARACTERISTICS("INSUFFICIENT_CHARACTERISTICS", "Password doesn't matches character rules."),
		INSUFFICIENT_COMPLEXITY("INSUFFICIENT_COMPLEXITY", "Password doesn't meets complexity rules."),
		INSUFFICIENT_COMPLEXITY_RULES("INSUFFICIENT_COMPLEXITY_RULES", "No rules have been configured for a password of that length."),
		SOURCE_VIOLATION("SOURCE_VIOLATION", "Password cannot be the same as your last password."),
		TOO_LONG("TOO_LONG", "Password must be no more than 8 characters in length."),
		TOO_SHORT("TOO_SHORT", "Password must be more characters in length."),
		TOO_MANY_OCCURRENCES("TOO_MANY_OCCURRENCES", "Password contains occurrences of the character.");

	String code;
	String message;

	public static Optional<PasswordValidatorErrorMessage> getById(final String code) {
		return Arrays.stream(values()).filter(item -> item.getCode().equalsIgnoreCase(code)).findFirst();
	}
}
