package org.fast.crawler.core.manager;

/**
 * 全局配置
 */
public class Constants {

	public static String USER_AGENT_CONFIG = "user.agent";
	public static String DEFAULT_COOKIES_STORAGE_PATH = "";
	public static String DEFAULT_USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:36.0) Gecko/20100101 Firefox/36.0";
	public static long requestMaxInterval = 1000 * 60 * 2;

	public static long WAIT_THREAD_END_TIME = 1000 * 60;
	public static int MAX_RECEIVE_SIZE = 1000 * 1000;
	public static int MAX_REDIRECT = 2;
	public static int retry = 3;

	public static int TIMEOUT_CONNECT = 3000;
	public static int TIMEOUT_READ = 10000;
	public static int MAX_RETRY = 20;

}
