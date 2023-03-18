package com.binance.crypto.bot.api.user.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserData {

	private String name;
	private String username;
	private String rawPassword;
	private Boolean active;
	private Boolean accountNonLocked;
	private Boolean qrCodeEnabled;

}
