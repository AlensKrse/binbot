package com.binance.crypto.bot.api.useractionlog.entity;

import com.binance.crypto.bot.api.common.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "user_action_logs")
public class UserActionLog extends AuditableEntity {

	@Column
	private Long userId;

	@Column
	private Long actionId;

	@Column
	private String action;

}
