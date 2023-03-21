package com.binance.crypto.bot.api.controller.user.model.service;

import com.binance.crypto.bot.api.role.entity.Role;
import com.binance.crypto.bot.api.user.data.UserData;
import com.binance.crypto.bot.api.user.entity.User;
import com.binance.crypto.bot.api.user.service.UserService;
import com.binance.crypto.bot.api.userpasswordhistories.service.UserPasswordHistoryService;
import com.binance.crypto.bot.utils.DateUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortalUserService {

    private final UserService userService;
    private final UserPasswordHistoryService userPasswordHistoryService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public List<UserData> findAllData() {
        return userService.findAll().stream().map(this::mapToUserData).sorted(Comparator.comparing(UserData::getName)).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveData(@NonNull final UserData userData) {
        create(userData);
    }

    @Transactional(readOnly = true)
    public UserData loadUserDataById(final long userId) {
        final User user = loadById(userId);
        return mapToUserData(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateData(final long userId, final UserData userData) {
        Validate.notNull(userData, "user is undefined");
        Validate.notBlank(userData.getUsername(), "username is blank");
        Validate.notBlank(userData.getName(), "name is blank");

        final User existingUser = userService.loadById(userId);

        final String password = userData.getPassword();
        if (StringUtils.isNotBlank(password)) {
            userService.validatePassword(password);
            existingUser.setPassword(passwordEncoder.encode(password));
        }

        existingUser.setName(userData.getName());
        existingUser.setActive(userData.getActive());
        existingUser.setRoleId(userData.getRoleId());

        userService.save(existingUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserData create(final UserData userData) {
        final User userToCreate = createUserEntityFromData(userData);

        final String encodedPassword = passwordEncoder.encode(userData.getPassword());
        final User user = userService.save(userToCreate, encodedPassword, userData.getPassword());
        Validate.notNull(user, "created user is undefined");
        userPasswordHistoryService.saveUserPassword(user.getId(), encodedPassword);

        return mapToUserData(loadById(user.getId()));
    }

    @Transactional(readOnly = true)
    public User loadById(final long userId) {
        return userService.loadById(userId);
    }


    private UserData mapToUserData(final User user) {
        final UserData data = new UserData();
        data.setId(user.getId());
        data.setName(user.getName());
        data.setUsername(user.getUsername());
        data.setRoleId(user.getRoleId());
        data.setRole(Role.Type.getTypeById(user.getRoleId()).getName());
        data.setActive(user.getActive());
        data.setAccountNonLocked(user.getAccountNonLocked());
        data.setQrCodeEnabled(user.getQrCodeEnabled());

        return data;
    }

    private User createUserEntityFromData(final UserData userData) {
        Validate.notNull(userData, "userData is undefined");
        final User user = new User();
        user.setName(userData.getName());
        user.setUsername(userData.getUsername());
        user.setActive(userData.getActive());
        user.setRoleId(userData.getRoleId());

        user.setLastLogin(new Date());
        user.setAccountNonLocked(userData.getAccountNonLocked());
        user.setFailedAttempt(0);
        user.setPasswordExpiry(DateUtils.minusDays(new Date(), 1));
        user.setLastRequest(new Date());

        return user;
    }
}
