package com.binance.crypto.bot.api.controller.ping;

import com.binance.crypto.bot.api.roles.entity.Role;
import com.binance.crypto.bot.queue.QueuePublisher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TestControllerUris.ROOT)
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private static final String RESPONSE = "pong";
    private static final String HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE = "HttpServletRequest is undefined";

    private final QueuePublisher queuePublisher;


    @PreAuthorize(Role.ADMIN_CLIENT_ROLES)
    @GetMapping(TestControllerUris.PING)
    public ResponseEntity<String> ping(final HttpServletRequest request) {
        Validate.notNull(request, HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE);
        log.info("Received ping request from '{}'", request.getRemoteAddr());

        return ResponseEntity.ok(RESPONSE);
    }

    @PreAuthorize(Role.ADMIN_CLIENT_ROLES)
    @PostMapping(TestControllerUris.MESSAGE)
    public String publishMessage(@RequestBody final String message, final HttpServletRequest request) {
        Validate.notNull(request, HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE);
        Validate.notBlank(message, "message is undefined");

        queuePublisher.publishMessage(message + "");

        return "index";
    }
}
