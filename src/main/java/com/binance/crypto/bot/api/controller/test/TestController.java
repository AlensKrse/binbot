package com.binance.crypto.bot.api.controller.test;

import com.binance.crypto.bot.api.common.response.MessageResourceResponse;
import com.binance.crypto.bot.api.role.entity.Role;
import com.binance.crypto.bot.queue.QueuePublisher;
import com.binance.crypto.bot.queue.QueueSubscriber;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TestControllerUris.ROOT)
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private static final String RESPONSE = "pong";
    private static final String HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE = "HttpServletRequest is undefined";

    private final QueuePublisher queuePublisher;
    private final QueueSubscriber queueSubscriber;


    @PreAuthorize(Role.ADMIN_CLIENT_ROLES)
    @GetMapping(TestControllerUris.PING)
    public MessageResourceResponse<String> ping(final HttpServletRequest request) {
        Validate.notNull(request, HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE);
        log.info("Received ping request from '{}'", request.getRemoteAddr());

        return MessageResourceResponse.success(RESPONSE);
    }

    @GetMapping("/postMessage-trade")
    public void publishTrade(
            @RequestParam("message") String message, @RequestParam("count") int messageCount) {
        for (int i = 0; i < messageCount; i++) {
            this.queuePublisher.publishTradeMessage(message);
        }
    }

    @GetMapping("/postMessage-callback")
    public void publishCallback(
            @RequestParam("message") String message, @RequestParam("count") int messageCount) {
        for (int i = 0; i < messageCount; i++) {
            this.queuePublisher.publishCallbackMessage(message);
        }
    }

    @GetMapping("/pull-callback")
    public void pullCallbackMessage() {
        this.queueSubscriber.pullCallbackMessage();
    }

    @GetMapping("/pull-trade")
    public void pullTradeMessage() {
        this.queueSubscriber.pullTradeMessage();
    }
}
