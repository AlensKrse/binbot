package com.binance.crypto.bot.api.useractionlog.service;

import com.binance.crypto.bot.api.actionlogtypes.entity.ActionLog;
import com.binance.crypto.bot.api.useractionlog.entity.UserActionLog;
import com.binance.crypto.bot.api.useractionlog.repository.UserActionLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserActionLogService {


    private static final int MIN_STRING_LENGTH = 0;
    private static final int MAX_STRING_LENGTH = 1024;

    private final UserActionLogRepository userActionLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logUserAction(final long userId, final ActionLog.Type logType, final String action) {
        Validate.notNull(logType, "Action log type is undefined");
        Validate.notBlank(action, "Action is blank");

        final String resultAction = action.length() > MAX_STRING_LENGTH ? action.substring(MIN_STRING_LENGTH, MAX_STRING_LENGTH) : action;
        final UserActionLog userActionLog = new UserActionLog(userId, logType.getId(), resultAction);
        userActionLogRepository.save(userActionLog);
        log.info(action);
    }
}
