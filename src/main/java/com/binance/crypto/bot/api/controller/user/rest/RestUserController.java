package com.binance.crypto.bot.api.controller.user.rest;

import com.binance.crypto.bot.api.actionlogtypes.entity.ActionLog;
import com.binance.crypto.bot.api.common.auth.service.AuthService;
import com.binance.crypto.bot.api.common.response.MessageResourceResponse;
import com.binance.crypto.bot.api.exception.user.UserCreationException;
import com.binance.crypto.bot.api.exception.user.UserListException;
import com.binance.crypto.bot.api.exception.user.UserLoginDataResetException;
import com.binance.crypto.bot.api.exception.user.UserPasswordExpirationException;
import com.binance.crypto.bot.api.exception.user.UserQrCodeRegenerationException;
import com.binance.crypto.bot.api.exception.user.UserRetrievingException;
import com.binance.crypto.bot.api.exception.user.UserUpdateException;
import com.binance.crypto.bot.api.role.entity.Role;
import com.binance.crypto.bot.api.user.data.UserData;
import com.binance.crypto.bot.api.user.service.PortalUserService;
import com.binance.crypto.bot.api.useractionlog.service.UserActionLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestUserControllerUris.ROOT)
@RequiredArgsConstructor
@Slf4j
public class RestUserController {

    private final PortalUserService portalUserService;
    private final AuthService authService;
    private final UserActionLogService userActionLogService;

    @PreAuthorize(Role.ADMIN_ROLE)
    @PutMapping
    public MessageResourceResponse<UserData> create(@NonNull @RequestBody final UserData userData, @NonNull final HttpServletRequest request) {
        try {
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
    public MessageResourceResponse<UserData> get(@PathVariable final long userId, @NonNull final HttpServletRequest request) {
        try {
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
    public MessageResourceResponse<List<UserData>> list(@NonNull final HttpServletRequest request) {
        try {
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

    @PreAuthorize(Role.ADMIN_ROLE)
    @PostMapping(RestUserControllerUris.USER)
    public MessageResourceResponse<UserData> updateUser(@PathVariable final long userId, @NonNull @RequestBody final UserData userData, @NonNull final HttpServletRequest request) {
        try {
            final long requestingUserId = authService.getUserId();
            final String message = String.format("User with id '%d' edit user from '%s' for user with id: '%d', userdata: '%s'", requestingUserId, request.getRemoteAddr(), userId, userData);
            log.info(message);
            userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, message);

            return MessageResourceResponse.success(portalUserService.updateData(userId, userData));
        } catch (final Exception e) {
            log.error(e.getMessage());
            return MessageResourceResponse.failure(new UserUpdateException(e.getMessage()));
        }
    }


    @PreAuthorize(Role.ADMIN_CLIENT_ROLES)
    @PutMapping(RestUserControllerUris.USER_LOGIN_DATA)
    public MessageResourceResponse<Boolean> resetUserLoginDataAfterSuccessfulAuth(@NonNull final HttpServletRequest httpRequest) {
        try {
            Validate.notNull(httpRequest, "Http request is undefined");
            final long userId = authService.getUserId();
            final String logMessage = String.format("Update user '%s' login data", userId);
            userActionLogService.logUserAction(userId, ActionLog.Type.USER, logMessage);

            portalUserService.resetUserLoginDataAfterSuccessfulAuth(userId, httpRequest.getRemoteAddr());
            return MessageResourceResponse.success(true);
        } catch (final Exception e) {
            log.error(e.getMessage());
            return MessageResourceResponse.failure(new UserLoginDataResetException(e.getMessage()));
        }
    }

    @PreAuthorize(Role.ADMIN_CLIENT_ROLES)
    @GetMapping(RestUserControllerUris.PASSWORD_EXPIRE_CHECK)
    public MessageResourceResponse<Boolean> isPasswordExpired() {
        try {
            final long userId = authService.getUserId();
            final String logMessage = String.format("Password expire check for user with id: '%d'", userId);
            userActionLogService.logUserAction(userId, ActionLog.Type.USER, logMessage);

            return MessageResourceResponse.success(portalUserService.isPasswordExpired(userId));
        } catch (final Exception e) {
            log.error(e.getMessage());
            return MessageResourceResponse.failure(new UserPasswordExpirationException(e.getMessage()));
        }
    }

    @PreAuthorize(Role.ADMIN_ROLE)
    @PostMapping(RestUserControllerUris.REGENERATE_QR_CODE)
    public MessageResourceResponse<Boolean> regenerateQrCode(final @PathVariable long userId) {
        try {
            final long requestingUserId = authService.getUserId();
            final String logMessage = String.format("User '%d' requested regenerate qr code for user with id '%d'", requestingUserId, userId);
            userActionLogService.logUserAction(requestingUserId, ActionLog.Type.USER, logMessage);

            return MessageResourceResponse.success(portalUserService.regenerateQrCode(userId));
        } catch (final Exception e) {
            log.error(e.getMessage());
            return MessageResourceResponse.failure(new UserQrCodeRegenerationException(e.getMessage()));
        }

    }

}
