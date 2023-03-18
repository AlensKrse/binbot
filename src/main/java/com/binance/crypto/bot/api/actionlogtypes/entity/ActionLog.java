package com.binance.crypto.bot.api.actionlogtypes.entity;

import com.binance.crypto.bot.api.common.AuditableEntity;
import com.binance.crypto.bot.utils.EnumUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "action_log_types")
public class ActionLog extends AuditableEntity {

	@AllArgsConstructor
	public enum Type implements EnumUtils.IdentifiableEnum<Long> {

			LOG_IN(1L),
			LOG_OUT(2L),
			PASSWORD(3L),
			ENDPOINT_CALL(4L),
			BUY(5L),
			SELL(6L),
			BALANCE(7L),
			USER(8L);


		private final long id;

		@Override
		public Long getId() {
			return this.id;
		}

		public static Optional<Type> getTypeById(final long id) {
			return Arrays.stream(values()).filter(item -> item.getId().equals(id)).findFirst();
		}

		public static Type getTypeByIdStrict(final long id) {
			return Arrays.stream(values())
				.filter(item -> item.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException(String.format("Action log type not found by ID %s", id)));
		}

	}

	@Column
	private String title;

}
