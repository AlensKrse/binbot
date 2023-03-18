package com.binance.crypto.bot.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	private static final String BACKEND_LONG_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String BACKEND_DATE_FORMAT = "yyyy-MM-dd";
	private static final DateTimeFormatter DATE_TIME_FORMATTER_LONG = DateTimeFormatter.ofPattern(BACKEND_LONG_FORMAT).withZone(ZoneId.systemDefault());

	private DateUtils() {}

	public static Date jsonDateTimeStringToBackendDateOrNull(final String jsonDate) {
		if (StringUtils.isNotBlank(jsonDate)) {
			try {
				return new SimpleDateFormat(BACKEND_LONG_FORMAT).parse(jsonDate);
			} catch (final ParseException e) {
				LOGGER.error(String.format("Invalid JSON date '%s'. Expected format is '%s'", jsonDate, BACKEND_LONG_FORMAT), e);
				return null;
			}
		} else {
			return null;
		}
	}

	public static LocalDate jsonDateStringToBackendDateOrNull(final String jsonDate) {
		if (StringUtils.isNotBlank(jsonDate)) {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BACKEND_DATE_FORMAT);
			return LocalDate.parse(jsonDate, formatter);
		} else {
			return null;
		}
	}

	public static Date jsonDateStringToBackendSimpleDateOrNull(final String jsonDate) {
		if (StringUtils.isNotBlank(jsonDate)) {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BACKEND_DATE_FORMAT);
			final LocalDate localDate = LocalDate.parse(jsonDate, formatter);
			return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		} else {
			return null;
		}
	}

	public static String formatAsLongDate(final Date date) {
		if (date == null) {
			return null;
		}

		return DATE_TIME_FORMATTER_LONG.format(date.toInstant());
	}

	public static Date addTimeZoneShiftToUtcDate(final Date utcDate, final String timezone) {
		if (utcDate == null) {
			return null;
		}

		final int offsetInMinutes = DateUtils.getTimeZoneMinutesOffset(utcDate, timezone);
		if (offsetInMinutes != 0) {
			final LocalDateTime localDateTime = new java.sql.Timestamp(utcDate.getTime()).toLocalDateTime();
			return Date.from(localDateTime.plusMinutes(offsetInMinutes).atZone(ZoneId.systemDefault()).toInstant());
		} else {
			return utcDate;
		}
	}

	public static int getTimeZoneMinutesOffset(final Date utcDate, final String timezone) {
		if (utcDate == null) {
			return 0;
		}

		if (StringUtils.isBlank(timezone)) {
			return 0;
		}

		final TimeZone tz = TimeZone.getTimeZone(timezone);
		return tz.getOffset(utcDate.getTime()) / 1000 / 60;
	}

	public static Date plusMinutes(final Date date, final int plusMinutes) {
		final LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusMinutes(plusMinutes);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date minusMinutes(final Date date, final int minusMinutes) {
		final LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).minusMinutes(minusMinutes);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static long getMinutesBetween(final Date dateFrom, final Date dateTo) {
		Validate.notNull(dateFrom, "dateFrom is undefined");
		Validate.notNull(dateTo, "dateTo is undefined");

		long duration = dateTo.getTime() - dateFrom.getTime();
		return TimeUnit.MILLISECONDS.toMinutes(duration);
	}

	public static Date plusDays(final Date date, final int plusDays) {
		final LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusDays(plusDays);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date minusDays(final Date date, final int minusDays) {
		final LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).minusDays(minusDays);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate dateToLocalDate(final Date date) {
		Validate.notNull(date, "Date is undefined");
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime dateToLocalDateTime(final Date date) {
		Validate.notNull(date, "Date is undefined");
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
