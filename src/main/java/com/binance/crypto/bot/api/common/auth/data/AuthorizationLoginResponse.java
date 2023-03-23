package com.binance.crypto.bot.api.common.auth.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorizationLoginResponse {

    boolean success;
    Long id;
    String username;
    Long roleId;
    String token;
    String type;
    boolean qrCodeGenerated;
    String otpAuthURL;

}
