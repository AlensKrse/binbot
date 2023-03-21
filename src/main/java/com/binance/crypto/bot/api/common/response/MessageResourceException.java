package com.binance.crypto.bot.api.common.response;

import java.util.Map;

public abstract class MessageResourceException extends RuntimeException {

	public abstract String getMessageResourceId();

	public abstract Map<String, String> getMessageResourceParameters();

	public abstract String getErrorMessage();

}
