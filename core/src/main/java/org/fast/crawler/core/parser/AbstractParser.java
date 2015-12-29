package org.fast.crawler.core.parser;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.core.message.MessageBus;


public abstract class AbstractParser implements Parser, Serializable {

	private static final long serialVersionUID = 1L;

	public void notifySubCrawlMessage(CrawlerSiteConfig siteConfig) {
		MessageBus.getInstance().addMessage(MessageBus.SUB_CRAWL_SITE_MSG, siteConfig);
	}

	public String toString(Object object) {
		if (object != null) {
			return object.toString();
		} else {
			return "";
		}
	}

	public Boolean toBoolean(Object object) {
		if (object != null) {
			return Boolean.valueOf(object.toString());
		} else {
			return false;
		}
	}

	public Integer toInteger(Object object) {
		if (object != null && StringUtils.isNotEmpty(object.toString())) {
			return Integer.valueOf(object.toString());
		} else {
			return 0;
		}
	}

	public Long toLong(Object object) {
		if (object != null && StringUtils.isNotEmpty(object.toString())) {
			return Long.valueOf(object.toString());
		} else {
			return 0L;
		}
	}
}
