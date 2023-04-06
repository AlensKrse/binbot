package com.binance.crypto.bot.api.backgroundjob.useractive;

import com.binance.crypto.bot.api.role.entity.Role;
import com.binance.crypto.bot.api.user.entity.User;
import com.binance.crypto.bot.api.user.repository.UserRepository;
import com.binance.crypto.bot.api.useractionlog.entity.UserActionLog;
import com.binance.crypto.bot.api.useractionlog.repository.UserActionLogRepository;
import com.binance.crypto.bot.utils.DateUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@SpringBootTest
class UserActivityCheckServiceTest {

    private static final int EXPIRATION_DAYS = 90;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserActionLogRepository userActionLogRepository;

    @Autowired
    private UserActivityCheckService userActivityCheckService;

    @Test
    void testSetInActiveExpiredUser() {
        createUser("User1", DateUtils.minusDays(new Date(), 20));
        createUser("User2", DateUtils.minusDays(new Date(), 91));
        createUser("User3", DateUtils.minusDays(new Date(), 200));
        createUser("User4", DateUtils.minusDays(new Date(), 100));
        createUser("User5", DateUtils.minusDays(new Date(), 89));

        userActivityCheckService.expirationDays = EXPIRATION_DAYS;
        userActivityCheckService.checkUserActivity();

        final List<User> users = userRepository.findAll().stream().sorted(Comparator.comparing(User::getName)).toList();
        MatcherAssert.assertThat(users.size(), Matchers.equalTo(5));

        final User user1 = users.get(0);
        MatcherAssert.assertThat(user1.getActive(), Matchers.equalTo(true));
        final User user2 = users.get(1);
        MatcherAssert.assertThat(user2.getActive(), Matchers.equalTo(false));
        final User user3 = users.get(2);
        MatcherAssert.assertThat(user3.getActive(), Matchers.equalTo(false));
        final User user4 = users.get(3);
        MatcherAssert.assertThat(user4.getActive(), Matchers.equalTo(false));
        final User user5 = users.get(4);
        MatcherAssert.assertThat(user5.getActive(), Matchers.equalTo(true));

        final List<UserActionLog> userActionLogs =
                userActionLogRepository.findAll().stream().sorted(Comparator.comparing(UserActionLog::getCreated)).toList();
        MatcherAssert.assertThat(userActionLogs.size(), Matchers.equalTo(3));

        final UserActionLog userActionLog1 = userActionLogs.get(0);
        MatcherAssert.assertThat(userActionLog1.getAction(),
                Matchers.containsString("After job user activity check user with id: '" + user2.getId() + "' set to inactive. Last login: "));

        final UserActionLog userActionLog2 = userActionLogs.get(1);
        MatcherAssert.assertThat(userActionLog2.getAction(),
                Matchers.containsString("After job user activity check user with id: '" + user3.getId() + "' set to inactive. Last login: "));

        final UserActionLog userActionLog3 = userActionLogs.get(2);
        MatcherAssert.assertThat(userActionLog3.getAction(),
                Matchers.containsString("After job user activity check user with id: '" + user4.getId() + "' set to inactive. Last login: "));
    }

    private void createUser(final String name, final Date lastLogin) {
        final User user = new User();
        user.setRoleId(Role.Type.CLIENT.getId());
        user.setName(name);
        user.setUsername("User1Name");
        user.setPassword("User1Password");
        user.setActive(true);
        user.setLastLogin(lastLogin);
        user.setFailedAttempt(0);
        user.setAccountNonLocked(true);
        user.setPasswordExpiry(DateUtils.plusDays(new Date(), 100));
        user.setLastRequest(new Date());

        userRepository.save(user);
    }


}