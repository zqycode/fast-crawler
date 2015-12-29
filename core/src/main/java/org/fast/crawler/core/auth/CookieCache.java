package org.fast.crawler.core.auth;

import org.apache.http.client.CookieStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CookieCache {

	private static Map<String, CookieStore> cookieStoreMap = new ConcurrentHashMap<String, CookieStore>();

	public static CookieStore get(String key) {
		return cookieStoreMap.get(key);
	}

	public static void set(String key, CookieStore cookieStore) {
		cookieStoreMap.put(key, cookieStore);
	}

}
