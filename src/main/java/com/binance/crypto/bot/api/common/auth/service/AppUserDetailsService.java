package com.binance.crypto.bot.api.common.auth.service;

import com.binance.crypto.bot.api.actionlogtypes.entity.ActionLog;
import com.binance.crypto.bot.api.role.entity.Role;
import com.binance.crypto.bot.api.role.service.RoleService;
import com.binance.crypto.bot.api.user.entity.User;
import com.binance.crypto.bot.api.user.service.UserService;
import com.binance.crypto.bot.api.useractionlog.service.UserActionLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppUserDetailsService implements UserDetailsService {

    UserService userService;
    RoleService roleService;
    UserActionLogService userActionLogService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final String lowercaseUsername = StringUtils.lowerCase(username);

        final User user = userService.findOneByUsername(lowercaseUsername)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The username %s doesn't exist", lowercaseUsername)));

        final List<GrantedAuthority> authorities = new ArrayList<>();
        final long roleId = Validate.notNull(user.getRoleId(), "Role is undefined for user %s", user);
        final Role role = roleService.getRoleById(roleId);
        authorities.add(new SimpleGrantedAuthority(role.getName()));

        final boolean enabled = user.getActive() && (!user.getDeleted());
        final boolean accountNonExpired = true;
        final boolean credentialsNonExpired = true;

        userActionLogService.logUserAction(user.getId(), ActionLog.Type.LOG_IN, "User log in");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
                user.getAccountNonLocked(), authorities);
    }
}
