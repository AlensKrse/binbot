package com.binance.crypto.bot;

import com.binance.crypto.bot.queue.QueuePublisher;
import com.binance.crypto.bot.queue.QueueSubscriber;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class BaseIntegrationTest {

    @MockBean
    private QueuePublisher queuePublisher;

    @MockBean
    private QueueSubscriber queueSubscriber;
}
