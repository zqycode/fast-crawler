package org.fast.crawler.core.job;

import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.core.auth.User;

public class CrawlContext {

	private static ThreadLocal<CrawlContextData> threadLocal = new ThreadLocal<CrawlContextData>();

	public static void set(CrawlContextData user) {
		threadLocal.set(user);
	}

	public static void unset() {
		threadLocal.remove();
	}

	public static CrawlContextData get() {
		return threadLocal.get();
	}

	public static CrawlerSiteConfig getSiteConfig() {
		CrawlContextData crawlContextData = get();
		if (crawlContextData != null) {
			return crawlContextData.getSiteConfig();
		}
		return null;
	}

	public static User getUser() {
		CrawlContextData crawlContextData = get();
		if (crawlContextData != null) {
			return crawlContextData.getUser();
		}
		return null;
	}
}
