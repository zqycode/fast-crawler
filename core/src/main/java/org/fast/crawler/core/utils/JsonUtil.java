package org.fast.crawler.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by xp017734 on 9/18/15.
 */
public class JsonUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String toJSON(Object object) {
		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static Map<String, Object> toMap(String json) {
		Map<String, Object> map = null;
		try {
			map = mapper.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

}
