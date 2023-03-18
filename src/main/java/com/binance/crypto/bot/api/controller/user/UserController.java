package com.binance.crypto.bot.api.controller.user;

import com.binance.crypto.bot.api.common.auth.AuthService;
import com.binance.crypto.bot.api.user.data.UserData;
import com.binance.crypto.bot.api.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserControllerUris.ROOT)
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping(UserControllerUris.CREATE)
    public ResponseEntity<UserData> create(final UserData userData, final HttpServletRequest request){
        try{
        Validate.notNull(userData, "UserData is undefined");
        Validate.notNull(request, "HttpServletRequest is undefined");
        log.info("Received user creation request from '{}', user: '{}', request: '{}'", userData, request, request.getRemoteAddr());
        final long userId = authService.getUserId();

        final UserData savedUserData = userService.create(userId, userData);
        return ResponseEntity.ok(savedUserData);
        } catch (final Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
