package com.binance.crypto.bot.api.common.response;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.Map;

@Getter
@Setter
public class MessageResourceResponse<T> {

    private boolean success;
    private String messageResource;
    private String errorMessage;
    private Map<String, String> messageResourceParameters;
    private T body;

    public MessageResourceResponse() {
    }

    protected MessageResourceResponse(final boolean success, final String messageResource, final Map<String, String> messageResourceParameters, final String errorMessage) {
        this.success = success;
        this.messageResource = messageResource;
        this.messageResourceParameters = messageResourceParameters;
        this.errorMessage = errorMessage;
    }

    protected MessageResourceResponse(final boolean success, final String messageResource, final Map<String, String> messageResourceParameters, T body) {
        this.success = success;
        this.messageResource = messageResource;
        this.messageResourceParameters = messageResourceParameters;
        this.body = body;
    }

    public static <T> MessageResourceResponse<T> success(final T body) {
        return new MessageResourceResponse<>(true, null, Collections.emptyMap(), body);
    }

    public static MessageResourceResponse success() {
        return new MessageResourceResponse<>(true, null, Collections.emptyMap(), null);
    }

    public static <T> MessageResourceResponse<T> failure(final String messageResource, final Map<String, String> messageResourceParameters, final String errorMessage) {
        Validate.notNull(messageResource, "messageResource is undefined");
        return new MessageResourceResponse<>(false, messageResource, messageResourceParameters, errorMessage);
    }

    public static <T> MessageResourceResponse<T> failure(final MessageResourceException messageResourceException) {
        Validate.notNull(messageResourceException, "messageResourceException is undefined");
        Validate.notBlank(messageResourceException.getMessageResourceId(), "messageResourceException.messageResourceId is blank");
        return new MessageResourceResponse<>(false, messageResourceException.getMessageResourceId(), messageResourceException.getMessageResourceParameters(), messageResourceException.getErrorMessage());
    }

}
