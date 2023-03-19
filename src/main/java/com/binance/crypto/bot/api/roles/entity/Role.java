package com.binance.crypto.bot.api.roles.entity;

import com.binance.crypto.bot.utils.EnumUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "roles")
public class Role {

	public static final String ADMINISTRATOR_ROLE_NAME = "ROLE_ADMINISTRATOR";
	public static final String CLIENT_ROLE_NAME = "ROLE_CLIENT";
	public static final String ADMIN_CLIENT_ROLES = "hasAnyRole('" + Role.ADMINISTRATOR_ROLE_NAME + "'," + "'" + Role.CLIENT_ROLE_NAME + "')";

	public enum Type implements EnumUtils.IdentifiableEnum<Long> {

			ADMINISTRATOR(1L, ADMINISTRATOR_ROLE_NAME),
			CLIENT(2L, CLIENT_ROLE_NAME);

		private final long id;
		private final String code;

		Type(final long id, final String code) {
			this.id = id;
			this.code = code;
		}

		public Long getId() {
			return id;
		}

		public String getCode() {
			return code;
		}

		public static Type getTypeById(final long id) {
			return Arrays.stream(values())
				.filter(item -> item.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new RuntimeException(String.format("Role type '%s' is not found", id)));
		}

		public boolean isAdminOrClient() {
			return this == ADMINISTRATOR || this == CLIENT;
		}

		public boolean isAdmin() {
			return this == ADMINISTRATOR;
		}

		public boolean isClient() {
			return this == CLIENT;
		}
	}

	@Id
	@Column
	protected Long id;

	@Column
	private String name;

}
