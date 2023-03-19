package com.binance.crypto.bot.api.controller.ping;

import com.binance.crypto.bot.config.PubSubConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(TestControllerUris.ROOT)
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private static final String RESPONSE = "pong";
    private static final String HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE = "HttpServletRequest is undefined";

    private final PubSubConfig.Publisher messagingGateway;

    @GetMapping(TestControllerUris.ROOT)
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(TestControllerUris.PING)
    public ResponseEntity<String> ping(final HttpServletRequest request){
        Validate.notNull(request, HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE);
        log.info("Received ping request from '{}'", request.getRemoteAddr());

        return ResponseEntity.ok(RESPONSE);
    }

    @PostMapping(TestControllerUris.MESSAGE)
    public RedirectView publishMessage(@RequestBody final String message, final HttpServletRequest request) {
        Validate.notNull(request, HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE);
        Validate.notBlank(message, "message is undefined");

        messagingGateway.sendToPubsub(message);

        return new RedirectView("/");
    }
}
