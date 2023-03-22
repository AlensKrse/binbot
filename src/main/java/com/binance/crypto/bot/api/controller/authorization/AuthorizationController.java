package com.binance.crypto.bot.api.controller.authorization;

import com.binance.crypto.bot.api.common.auth.data.AuthorizationLoginRequest;
import com.binance.crypto.bot.api.common.auth.data.AuthorizationLoginResponse;
import com.binance.crypto.bot.api.common.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping(AuthorizationControllerUris.ROOT)
@Slf4j
public class AuthorizationController {
    private final AuthService authService;

    @PostMapping(AuthorizationControllerUris.LOGIN)
    public ResponseEntity<AuthorizationLoginResponse> authenticateUser(@Valid @RequestBody final AuthorizationLoginRequest loginRequest, @NonNull final HttpServletRequest request) {
        log.info("Received login request from: '{}', request: '{}'", request, loginRequest);
        final AuthorizationLoginResponse response = authService.authenticateUser(loginRequest);

        return ResponseEntity.ok(response);
    }
}
