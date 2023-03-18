package com.binance.crypto.bot.api.userpasswordhistories.entity;

import com.binance.crypto.bot.api.common.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_password_histories")
public class UserPasswordHistory extends AuditableEntity {

	@Column
	Long userId;

	@Column
	String password;

}
