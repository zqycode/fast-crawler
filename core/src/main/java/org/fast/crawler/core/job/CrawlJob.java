package org.fast.crawler.core.job;

import org.fast.crawler.core.configuration.CrawlerSiteConfig;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlJob extends AbstarctJob {

	private Logger logger = LoggerFactory.getLogger(CrawlJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		CrawlerSiteConfig siteConfig = (CrawlerSiteConfig) context.getMergedJobDataMap().get("SITE-CONFIG");
		if (siteConfig != null) {
			try {
				CrawlContextData crawlContextData = new CrawlContextData(siteConfig);
				CrawlContext.set(crawlContextData);

				logger.debug("begin to crawler " + siteConfig.getUrl());
				CrawlWorker worker = new CrawlWorker();
				worker.start();
				logger.debug("crawler end " + siteConfig.getUrl());
			} finally {
				CrawlContext.unset();
			}

		}
	}

}
