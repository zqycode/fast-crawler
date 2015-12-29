package org.fast.crawler.core.job;

import org.fast.crawler.core.auth.User;
import org.fast.crawler.core.configuration.CrawlerSiteConfig;

/**
 * Created by xp017734 on 10/12/15.
 */
public class CrawlContextData {

	private CrawlerSiteConfig siteConfig;

	private User user;

	protected CrawlContextData(CrawlerSiteConfig siteConfig) {
		this.siteConfig = siteConfig;
		if (this.siteConfig.getAuthProvider() != null) {
			this.user = this.siteConfig.getAuthProvider().getUser();
		}
	}

	public CrawlerSiteConfig getSiteConfig() {
		return siteConfig;
	}

	public User getUser() {
		return user;
	}

}
