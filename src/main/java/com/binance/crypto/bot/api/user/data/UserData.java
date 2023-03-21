package com.binance.crypto.bot.api.user.data;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserData {

    Long id;
    String name;
    String username;
    Long roleId;
    String rawPassword;
    Boolean active;
    Boolean accountNonLocked;
    Boolean qrCodeEnabled;

}
