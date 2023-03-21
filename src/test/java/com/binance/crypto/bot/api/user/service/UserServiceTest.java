package com.binance.crypto.bot.api.user.service;

import com.binance.crypto.bot.api.user.entity.User;
import com.binance.crypto.bot.api.user.repository.UserRepository;
import com.binance.crypto.bot.utils.DateUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final long USER_ID = 123L;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testUserPasswordCheck() {
        final User user = new User();
        final Date userPasswordExpire1 = DateUtils.minusDays(new Date(), 31);
        user.setPasswordExpiry(userPasswordExpire1);
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        final boolean passwordExpired1 = userService.isPasswordExpired(USER_ID);
        MatcherAssert.assertThat(true, Matchers.equalTo(passwordExpired1));

        final Date userPasswordExpire2 = DateUtils.plusDays(new Date(), 31);
        user.setPasswordExpiry(userPasswordExpire2);
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        final boolean passwordExpired2 = userService.isPasswordExpired(USER_ID);
        MatcherAssert.assertThat(false, Matchers.equalTo(passwordExpired2));
    }

}