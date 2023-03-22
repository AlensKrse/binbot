package com.binance.crypto.bot.api.controller.user.rest;

import com.binance.crypto.bot.api.actionlogtypes.entity.ActionLog;
import com.binance.crypto.bot.api.common.auth.service.AuthService;
import com.binance.crypto.bot.api.common.response.MessageResourceResponse;
import com.binance.crypto.bot.api.controller.user.model.service.PortalUserService;
import com.binance.crypto.bot.api.exception.user.UserCreationException;
import com.binance.crypto.bot.api.exception.user.UserListException;
import com.binance.crypto.bot.api.exception.user.UserRetrievingException;
import com.binance.crypto.bot.api.role.entity.Role;
import com.binance.crypto.bot.api.user.data.UserData;
import com.binance.crypto.bot.api.useractionlog.service.UserActionLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestUserControllerUris.ROOT)
@RequiredArgsConstructor
@Slf4j
public class RestUserController {

    private static final String HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE = "HttpServletRequest is undefined";

    private final PortalUserService portalUserService;
    private final AuthService authService;
    private final UserActionLogService userActionLogService;

    @PreAuthorize(Role.ADMIN_ROLE)
    @PostMapping(value = RestUserControllerUris.CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageResourceResponse<UserData> create(@RequestBody final UserData userData, final HttpServletRequest request) {
        try {
            Validate.notNull(userData, "UserData is undefined");
            Validate.notNull(request, HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE);

            final long requestingUserId = authService.getUserId();
            final String message = String.format("User with id '%d' requested user creation from '%s', userdata: '%s'", requestingUserId, request.getRemoteAddr(), userData);
            log.info(message);
            userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, message);

            final UserData savedUserData = portalUserService.create(userData);
            return MessageResourceResponse.success(savedUserData);
        } catch (final Exception e) {
            log.error(e.getMessage());
            return MessageResourceResponse.failure(new UserCreationException(e.getMessage()));
        }
    }

    @PreAuthorize(Role.ADMIN_CLIENT_ROLES)
    @GetMapping(RestUserControllerUris.USER)
    public MessageResourceResponse<UserData> get(@PathVariable final Long userId, final HttpServletRequest request) {
        try {
            Validate.notNull(userId, "userId is undefined");
            Validate.notNull(request, HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE);

            final long requestingUserId = authService.getUserId();
            final String message = String.format("User with id '%d' requested get user from '%s', requestedUserId: '%d'", requestingUserId, request.getRemoteAddr(), userId);
            log.info(message);
            userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, message);

            return MessageResourceResponse.success(portalUserService.loadUserDataById(userId));
        } catch (final Exception e) {
            log.error(e.getMessage());
            return MessageResourceResponse.failure(new UserRetrievingException(e.getMessage()));
        }
    }

    @PreAuthorize(Role.ADMIN_ROLE)
    @GetMapping(RestUserControllerUris.LIST)
    public MessageResourceResponse<List<UserData>> findAll(final HttpServletRequest request) {
        try {
            Validate.notNull(request, HTTP_SERVLET_REQUEST_IS_UNDEFINED_MESSAGE);

            final long requestingUserId = authService.getUserId();
            final String message = String.format("User with id '%d' requested list of users from '%s'", requestingUserId, request.getRemoteAddr());
            log.info(message);
            userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, message);


            return MessageResourceResponse.success(portalUserService.findAllData());
        } catch (final Exception e) {
            log.error(e.getMessage());
            return MessageResourceResponse.failure(new UserListException(e.getMessage()));
        }
    }

}
