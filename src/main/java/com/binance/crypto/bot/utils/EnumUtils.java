package com.binance.crypto.bot.utils;

public class EnumUtils {

	public interface IdentifiableEnum<T> {

		T getId();
	}

	private EnumUtils() {}

	public static <T extends IdentifiableEnum> T getById(final Long id, final T[] values) {
		if (id == null) {
			return null;
		} else {
			for (final T type : values) {
				if (type.getId().equals(id)) {
					return type;
				}
			}
			throw new IllegalArgumentException(String.format("Unable to find a type by id %s", id));
		}
	}

	public static <T extends IdentifiableEnum> boolean isIn(final Long id, final T... types) {
		if (id == null) {
			return false;
		} else {
			for (final T type : types) {
				if (type.getId().equals(id)) {
					return true;
				}
			}
			return false;
		}
	}


}
