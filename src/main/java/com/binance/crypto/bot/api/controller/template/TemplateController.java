package com.binance.crypto.bot.api.controller.template;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class TemplateController {

    @GetMapping(TemplateControllerUris.LOGIN)
    public String getLoginView(final HttpServletRequest request) {
        Validate.notNull(request, "Request is undefined");
        log.info("Received an login request from : '{}'", request.getRemoteAddr());

        return "login";
    }

    @GetMapping(TemplateControllerUris.DASHBOARD)
    public String getDashboard(final HttpServletRequest request) {
        Validate.notNull(request, "Request is undefined");
        log.info("Received an dashboard request from : '{}'", request.getRemoteAddr());

        return "dashboard";
    }
}
