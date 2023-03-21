package com.binance.crypto.bot.queue;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.pubsub.v1.PubsubMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueSubscriber {

    @Value("${google.cloud.pub.sub.callback.subscription.name}")
    String callbackSubscriptionName;

    @Value("${google.cloud.pub.sub.trade.subscription.name}")
    String tradeSubscriptionName;

    private final PubSubTemplate pubSubTemplate;

    public void pullCallbackMessage() {
        final List<PubsubMessage> messages = this.pubSubTemplate.pullAndAck(callbackSubscriptionName, 100, true);
        log.info("Pulled {} callback message(s)", messages.size());
        messages.forEach(message -> log.info("Pulled a callback message: '{}', data: '{}'", message, message.getData().toStringUtf8()));
    }

    public void pullTradeMessage() {
        final List<PubsubMessage> messages = this.pubSubTemplate.pullAndAck(tradeSubscriptionName, 100, true);
        log.info("Pulled {} trade message(s)", messages.size());
        messages.forEach(message -> log.info("Pulled a trade message: '{}', data: '{}'", message, message.getData().toStringUtf8()));
    }

}


