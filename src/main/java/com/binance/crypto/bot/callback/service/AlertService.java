package com.binance.crypto.bot.callback.service;

import com.binance.crypto.bot.callback.telegram.TelegramBotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AlertService {

    TelegramBotService telegramBotService;

    public void send(final Exception exception) {
        telegramBotService.send(exception);
    }
}
