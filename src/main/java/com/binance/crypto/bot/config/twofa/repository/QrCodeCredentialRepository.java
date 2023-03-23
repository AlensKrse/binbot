package com.binance.crypto.bot.config.twofa.repository;

import com.binance.crypto.bot.api.user.entity.User;
import com.binance.crypto.bot.api.user.service.UserService;
import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QrCodeCredentialRepository implements ICredentialRepository {

    public static final String DELIMITER = ",";

    private final UserService userService;

    @Override
    public String getSecretKey(final String username) {
        final User user = getUser(username);
        return user.getQrCodeSecret();
    }

    @Override
    public void saveUserCredentials(final String username, final String secretKey, final int validationCode, final List<Integer> scratchCodes) {
        final User user = getUser(username);
        user.setQrCodeSecret(secretKey);
        user.setQrCodeValidationCode(validationCode);
        user.setQrCodeScratchCodes(getQrCodeScratchCodes(scratchCodes));

        userService.save(user);
    }

    private String getQrCodeScratchCodes(final List<Integer> scratchCodes) {
        return String.join(DELIMITER, scratchCodes.stream().map(String::valueOf).toList());
    }

    private User getUser(final String username) {
        final String lowercaseUsername = StringUtils.lowerCase(username);
        final User user = userService.findOneByUsername(lowercaseUsername)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The username %s doesn't exist", lowercaseUsername)));
        Validate.isTrue(user.getActive(), "User is not active");

        return user;
    }

}