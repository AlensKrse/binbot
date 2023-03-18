package com.binance.crypto.bot.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class SecurityUtils {

	private SecurityUtils() {}

	public static String stripHtml(final String value) {
		if (StringUtils.isNotBlank(value)) {
			return Jsoup.clean(value, Safelist.none());
		} else {
			return value;
		}
	}

}
