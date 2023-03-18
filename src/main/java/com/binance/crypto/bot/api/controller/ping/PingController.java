package com.binance.crypto.bot.api.controller.ping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PingControllerUris.ROOT)
@RequiredArgsConstructor
@Slf4j
public class PingController {

    public static final String RESPONSE = "pong";

    @GetMapping
    public ResponseEntity<String> ping(final HttpServletRequest request){
        Validate.notNull(request, "HttpServletRequest is undefined");
        log.info("Received ping request from '{}'", request.getRemoteAddr());

        return ResponseEntity.ok(RESPONSE);
    }
}
