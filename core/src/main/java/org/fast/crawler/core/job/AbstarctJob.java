package org.fast.crawler.core.job;

import org.quartz.Job;

import org.fast.crawler.core.manager.CrawlerManager;
import org.fast.crawler.core.utils.SpringUtil;

public abstract class AbstarctJob implements Job {

	protected CrawlerManager getCrawlerManager() {
		CrawlerManager crawlerManager = SpringUtil.getBean(CrawlerManager.class);
		return crawlerManager;
	}

}
