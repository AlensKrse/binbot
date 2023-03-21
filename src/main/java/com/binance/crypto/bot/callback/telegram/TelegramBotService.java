package com.binance.crypto.bot.callback.telegram;

import com.binance.crypto.bot.callback.telegram.response.TelegramBotResponse;
import com.binance.crypto.bot.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramBotService {

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.chat.id}")
    private String chatId;

    @Value("${telegram.bot.url}")
    private String telegramUrl;

    @Value("${spring.application.name}")
    private String applicationName;

    private final RestTemplate restTemplate;

    public void send(final Exception e) {
        final String message = getFullMessage(e);
        sendMessage(message);
    }

    public void send(final String message) {
        final String fullMessage = getFullMessage(message);
        sendMessage(fullMessage);
    }

    private void sendMessage(final String message) {
        if (isTelegramBotAvailable()) {
            log.info("Sending telegram message {} ", message);

            final HttpEntity<MultiValueMap<String, String>> httpRequest = getMultiValueMapHttpEntity(message);
            final ResponseEntity<String> responseEntity = restTemplate.exchange(getTelegramApiUrl(), HttpMethod.POST, httpRequest, String.class);
            final TelegramBotResponse telegramBotResponse = JsonUtils.toObjectViaMapper(responseEntity.getBody(), TelegramBotResponse.class);
            log.info("Received response from Telegram API: {}", telegramBotResponse);
        }
    }

    private boolean isTelegramBotAvailable() {
        return StringUtils.isNotBlank(this.telegramUrl) && StringUtils.isNotBlank(this.chatId) && StringUtils.isNotBlank(this.token);
    }

    private String getFullMessage(final Exception e) {
        final StackTraceElement[] stackTrace = e.getStackTrace();
        final StackTraceElement stackTraceElement = stackTrace[0];
        final String classLoaderName = stackTraceElement.getClassName();
        final int lineNumber = stackTraceElement.getLineNumber();
        return getMessageHeader() + e.getMessage() + " | " + classLoaderName + "." + lineNumber;
    }

    private String getFullMessage(final String message) {
        return getMessageHeader() + message;
    }

    private String getMessageHeader() {
        return "Application: *" + applicationName + "*\n";
    }

    private String getTelegramApiUrl() {
        return telegramUrl + token + "/sendMessage";
    }

    private HttpEntity<MultiValueMap<String, String>> getMultiValueMapHttpEntity(final String notificationMessage) {
        final MultiValueMap<String, String> params = getParams(notificationMessage);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity<>(params, headers);
    }

    private MultiValueMap<String, String> getParams(final String notificationMessage) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("chat_id", chatId);
        params.add("chat_type", "private");
        params.add("text", notificationMessage);
        return params;
    }

}
