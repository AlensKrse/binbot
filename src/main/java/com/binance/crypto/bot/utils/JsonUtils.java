package com.binance.crypto.bot.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

public class JsonUtils {

	private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
	private static final ObjectMapper MAPPER;

	static {
		MAPPER = JsonMapper.builder().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build();
	}

	private JsonUtils() {}

	public static String toJson(final Object model) {
		if (model == null) {
			return null;
		}

		return GSON.toJson(model);
	}

	public static <T> T toObjectViaMapper(final String json, final Class<T> clazz) {
		if (clazz == null) {
			return null;
		}

		if (StringUtils.isBlank(json)) {
			return null;
		}

		try {
			return MAPPER.readValue(json, clazz);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unable to map '" + json + "' to class " + clazz.getCanonicalName(), e);
		}
	}

}
