package com.binance.crypto.bot.api.backgroundjob.useractive;

import com.binance.crypto.bot.api.actionlogtypes.entity.ActionLog;
import com.binance.crypto.bot.api.user.service.UserService;
import com.binance.crypto.bot.api.useractionlog.service.UserActionLogService;
import com.binance.crypto.bot.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserActivityCheckService {

    @Value("${user.activity.expiration.days}")
    Integer expirationDays;

    private final UserService userService;
    private final UserActionLogService userActionLogService;

    @Transactional
    public void checkUserActivity() {
        userService.findAll().forEach(user -> {
            final LocalDate lastLogin = DateUtils.dateToLocalDate(user.getLastLogin());
            final LocalDate expirationDate = lastLogin.plusDays(expirationDays);
            final LocalDate now = LocalDate.now();
            if (expirationDate.isBefore(now)) {
                final long userId = user.getId();
                userActionLogService.logUserAction(userId, ActionLog.Type.USER,
                        String.format(
                                "After job user activity check user with id: '%d' set to inactive. Last login: '%s', expiration date: '%s', system date: '%s'", userId,
                                lastLogin, expirationDate, now));
                log.info("Check user activity job found expired user with id: '{}', last login: '{}', expiration date: '{}', system date: '{}'",
                        userId, lastLogin, expirationDate, now);
                user.setActive(false);
            }
        });

    }

}
