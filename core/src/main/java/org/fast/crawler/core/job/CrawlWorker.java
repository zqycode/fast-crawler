package org.fast.crawler.core.job;

import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.core.fetcher.Fetcher;
import org.fast.crawler.core.fetcher.HttpFetcher;
import org.fast.crawler.core.fetcher.response.ResponseWrapper;
import org.fast.crawler.core.parser.Parser;

public class CrawlWorker {

	private CrawlerSiteConfig siteConfig;

	public CrawlWorker() {
		this.siteConfig = CrawlContext.getSiteConfig();
	}

	public void start() {
		Fetcher fetcher = new HttpFetcher();
		Parser extractor = this.siteConfig.getParser();

		if (fetcher != null && extractor != null) {
			ResponseWrapper<?> response = fetcher.startCrawl();
			extractor.parse(response);
		}
	}

}
