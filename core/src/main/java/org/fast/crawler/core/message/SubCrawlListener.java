package org.fast.crawler.core.message;

import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.core.manager.CrawlerManager;
import org.fast.crawler.core.message.MessageBus.Message;

public class SubCrawlListener implements Listener {

	private CrawlerManager crawlerManager;

	public SubCrawlListener(CrawlerManager crawlerManager) {
		this.crawlerManager = crawlerManager;
	}

	@Override
	public void handle(Message message) {
		CrawlerSiteConfig siteConfig = (CrawlerSiteConfig) message.getData();
		crawlerManager.addSiteJob(siteConfig);
	}

}
