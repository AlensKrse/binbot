package com.binance.crypto.bot.api.controller.template;

import com.binance.crypto.bot.api.common.auth.service.AuthService;
import com.binance.crypto.bot.api.role.entity.Role;
import com.binance.crypto.bot.api.user.data.UserData;
import com.binance.crypto.bot.api.user.service.PortalUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TemplateController {

    private final AuthService authService;
    private final PortalUserService portalUserService;

    @PreAuthorize(Role.ADMIN_CLIENT_ROLES)
    @GetMapping(TemplateControllerUris.INDEX)
    public String redirectToDashboard() {
        return "redirect:dashboard";
    }

    @GetMapping(TemplateControllerUris.LOGIN)
    public String getLoginView(final HttpServletRequest request) {
        Validate.notNull(request, "Request is undefined");
        log.info("Received an login request from : '{}'", request.getRemoteAddr());

        return "login";
    }

    @PreAuthorize(Role.ADMIN_CLIENT_ROLES)
    @GetMapping(TemplateControllerUris.DASHBOARD)
    public String getDashboard(@NonNull final Model model, final HttpServletRequest request) {
        Validate.notNull(request, "Request is undefined");
        log.info("Received an dashboard request from : '{}'", request.getRemoteAddr());

        final long userId = authService.getUserId();
        final UserData user = portalUserService.loadUserDataById(userId);
        model.addAttribute("user", user);

        return "dashboard";
    }
}
