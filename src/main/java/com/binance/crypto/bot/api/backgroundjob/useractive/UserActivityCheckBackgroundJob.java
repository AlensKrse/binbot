package com.binance.crypto.bot.api.backgroundjob.useractive;

import com.binance.crypto.bot.callback.service.AlertService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserActivityCheckBackgroundJob {


    UserActivityCheckService userActivityCheckService;

    AlertService alertService;

    @Scheduled(cron = "${background.jobs.user.activity.cron}")
    private void checkUserActivity() {
        log.info("Start of User activity check job");
        try {
            userActivityCheckService.checkUserActivity();
        } catch (final Exception e) {
            log.error("Unable to run User activity check", e);
            alertService.send(e);
        }
        log.info("Finish User activity check.");
    }
}
