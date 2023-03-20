package com.binance.crypto.bot.queue;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueuePublisher {

    @Value("${google.cloud.pub.sub.callback.topic.name}")
    String callbackTopicName;


    @Value("${google.cloud.pub.sub.trade.topic.name}")
    String tradeTopicName;

    private final PubSubTemplate pubSubTemplate;


    public void publishCallbackMessage(final String message) {
        Validate.notBlank(message, "Message is undefined");
        log.info("Send callback message: '{}'", message);

        pubSubTemplate.publish(callbackTopicName, message);
    }

    public void publishTradeMessage(final String message) {
        Validate.notBlank(message, "Message is undefined");
        log.info("Send trade message: '{}'", message);

        pubSubTemplate.publish(tradeTopicName, message);
    }

}
