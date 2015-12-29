package org.fast.crawler.core.auth;

import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.core.job.CrawlContext;
import org.fast.crawler.core.manager.CrawlerManager;
import org.fast.crawler.core.utils.SpringUtil;

import org.apache.http.client.CookieStore;

public abstract class AbstractAuthProvider implements AuthProvider {

	private static final String CRAWLER_COOKIES_STORAGE_PATH = "crawler.cookies.storage.path";

	protected CrawlerManager crawlerManager;

	private User user;

	public AbstractAuthProvider() {
		this.crawlerManager = SpringUtil.getBean(CrawlerManager.class);
		this.user = this.loadUser();
	}

	/**
	 * 加载用户信息
	 * 
	 * @return
	 */
	public abstract User loadUser();

	public CrawlerSiteConfig getSiteConfig() {
		return CrawlContext.getSiteConfig();
	}

	public User getUser() {
		if (this.user == null)
			throw new RuntimeException("用户未设置！");
		return this.user;
	}

	public abstract CookieStore doAuth();

	public CookieStore getCookies() {
		String cookieStoragePath = this.crawlerManager.getCrawlerProperty(CRAWLER_COOKIES_STORAGE_PATH);
		String cookiePath = cookieStoragePath + "/" + this.getCookieName();
		if (CookieCache.get(cookiePath) != null) {
			return CookieCache.get(cookiePath);
		} else {
			CookieStore cookieStore = this.doAuth();
			CookieCache.set(cookiePath, cookieStore);
			return cookieStore;
		}
	}

}
