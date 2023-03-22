package com.binance.crypto.bot.api.controller.user.model.controller;

import com.binance.crypto.bot.api.actionlogtypes.entity.ActionLog;
import com.binance.crypto.bot.api.common.auth.service.AuthService;
import com.binance.crypto.bot.api.controller.user.model.service.PortalUserService;
import com.binance.crypto.bot.api.role.entity.Role;
import com.binance.crypto.bot.api.user.data.UserData;
import com.binance.crypto.bot.api.useractionlog.service.UserActionLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ModelUserControllerUris.ROOT)
@RequiredArgsConstructor
@Slf4j
public class ModelUserController {

    private final PortalUserService portalUserService;
    private final AuthService authService;
    private final UserActionLogService userActionLogService;

    @PreAuthorize(Role.ADMIN_ROLE)
    @GetMapping
    public String listUsers(@NonNull final Model model, @NonNull final HttpServletRequest request) {
        final long requestingUserId = authService.getUserId();
        final String message = String.format("User with id '%d' requested model user list from '%s'", requestingUserId, request.getRemoteAddr());
        log.info(message);
        userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, message);

        model.addAttribute("users", portalUserService.findAllData());
        return "user/users";
    }


    @PreAuthorize(Role.ADMIN_ROLE)
    @GetMapping(ModelUserControllerUris.NEW)
    public String createUserForm(@NonNull final Model model, @NonNull final HttpServletRequest request) {
        final long requestingUserId = authService.getUserId();
        final String message = String.format("User with id '%d' requested create new model user from '%s'", requestingUserId, request.getRemoteAddr());
        log.info(message);
        userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, message);

        model.addAttribute("user", new UserData());
        return "user/create_user";

    }

    @PreAuthorize(Role.ADMIN_ROLE)
    @PostMapping
    public String saveUser(@NonNull @ModelAttribute("user") final UserData userData, @NonNull final HttpServletRequest request) {
        final long requestingUserId = authService.getUserId();
        final String message = String.format("User with id '%d' save new model user from '%s', userdata: '%s'", requestingUserId, request.getRemoteAddr(), userData);
        log.info(message);
        userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, message);

        portalUserService.saveData(userData);
        return "redirect:user/users";
    }

    @PreAuthorize(Role.ADMIN_ROLE)
    @GetMapping(ModelUserControllerUris.EDIT)
    public String editUserForm(@PathVariable final long userId, @NonNull final Model model, @NonNull final HttpServletRequest request) {
        final long requestingUserId = authService.getUserId();
        final String message = String.format("User with id '%d' requested edit model user from '%s' for user with id: '%d'", requestingUserId, request.getRemoteAddr(), userId);
        log.info(message);
        userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, message);

        model.addAttribute("user", portalUserService.loadUserDataById(userId));
        return "user/edit_user";
    }

    @PreAuthorize(Role.ADMIN_ROLE)
    @PostMapping(ModelUserControllerUris.UPDATE)
    public String updateUser(@PathVariable final long userId,
                             @NonNull @ModelAttribute("user") final UserData userData, @NonNull final HttpServletRequest request) {
        final long requestingUserId = authService.getUserId();
        final String message = String.format("User with id '%d' edit model user from '%s' for user with id: '%d', userdata: '%s'", requestingUserId, request.getRemoteAddr(), userId, userData);
        log.info(message);
        userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, message);

        portalUserService.updateData(userId, userData);
        return "redirect:/users";
    }

}
