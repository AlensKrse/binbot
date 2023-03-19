package com.binance.crypto.bot.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueuePublisher {

    private final PubSubConfig.Publisher callbackPublisher;


    public void publishMessage(final String message) {
        Validate.notBlank(message, "Message is undefined");
        log.info("Send message: '{}'", message);

        callbackPublisher.sendToPubsub(message);
    }

}
