package com.binance.crypto.bot.api.user.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserData {

    private Long id;
    private String name;
    private String username;
    private Long roleId;
    private String rawPassword;
    private Boolean active;
    private Boolean accountNonLocked;
    private Boolean qrCodeEnabled;

}
