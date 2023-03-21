package com.binance.crypto.bot.config;

import com.binance.crypto.bot.queue.QueuePublisher;
import com.binance.crypto.bot.queue.QueueSubscriber;
import com.google.api.gax.core.CredentialsProvider;
import com.google.auth.Credentials;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestGcpConfiguration {

    @Bean
    public CredentialsProvider credentialsProvider() throws IOException {
        Credentials mockCredentials = mock(Credentials.class);
        CredentialsProvider mockCredentialsProvider = mock(CredentialsProvider.class);
        when(mockCredentialsProvider.getCredentials()).thenReturn(mockCredentials);
        return mockCredentialsProvider;
    }

    @Bean
    public QueuePublisher queuePublisher() {
        return new QueuePublisher(mock(PubSubTemplate.class));
    }

    @Bean
    public QueueSubscriber queueSubscriber() {
        return mock(QueueSubscriber.class);
    }

}
