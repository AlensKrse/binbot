package com.binance.crypto.bot.api.common.auth.service;

import com.binance.crypto.bot.api.common.auth.data.AuthorizationLoginRequest;
import com.binance.crypto.bot.api.common.auth.data.AuthorizationLoginResponse;
import com.binance.crypto.bot.api.user.entity.User;
import com.binance.crypto.bot.api.user.service.UserService;
import com.binance.crypto.bot.config.jwt.JwtUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.Validate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    private static final String BEARER = "Bearer";

    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    UserService userService;

    @Transactional(readOnly = true)
    public long getUserId() {
        final User user = getCurrentUser();
        return Validate.notNull(user.getId(), "id is undefined for user: %s", user);
    }

    private User getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Validate.notNull(authentication, "authentication is undefined");

        final String username = Validate.notBlank(authentication.getName(), "name is blank for authentication: %s", authentication);
        final User user = userService.loadOneByUsernameAndActiveIsTrue(username);
        return Validate.notNull(user, "could not find active user with username '%s'", username);
    }

    public AuthorizationLoginResponse authenticateUser(final AuthorizationLoginRequest loginRequest) {
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtils.generateJwtToken(authentication);

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final User user = userService.loadOneByUsernameAndActiveIsTrue(userDetails.getUsername());

        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return AuthorizationLoginResponse.builder().id(user.getId()).username(user.getUsername()).roles(roles).token(token).type(BEARER).build();
    }
}
