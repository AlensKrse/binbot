package com.binance.crypto.bot.api.controller.daemon;

import com.binance.crypto.bot.api.actionlogtypes.entity.ActionLog;
import com.binance.crypto.bot.api.common.auth.AuthService;
import com.binance.crypto.bot.api.role.entity.Role;
import com.binance.crypto.bot.api.useractionlog.service.UserActionLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(DaemonControllerUris.ROOT)
@RequiredArgsConstructor
@Slf4j
public class DaemonController {

    private final AuthService authService;
    private final UserActionLogService userActionLogService;

    @PreAuthorize(Role.ADMIN_CLIENT_ROLES)
    @GetMapping
    public String listDaemons(@NonNull final Model model, @NonNull final HttpServletRequest request) {
        final long requestingUserId = authService.getUserId();
        final String message = String.format("User with id '%d' requested model daemon list from '%s'", requestingUserId, request.getRemoteAddr());
        log.info(message);
        userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, message);

        return "daemon/daemons";
    }
}
