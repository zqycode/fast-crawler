package org.fast.crawler.core.configuration;

import java.util.List;

public class CrawlerConfig {

	private Boolean useProxy = false;

	private List<CrawlerSiteConfig> sites;

	public Boolean getUseProxy() {
		return useProxy;
	}

	public void setUseProxy(Boolean useProxy) {
		this.useProxy = useProxy;
	}

	public List<CrawlerSiteConfig> getSites() {
		return sites;
	}

	public void setSites(List<CrawlerSiteConfig> sites) {
		this.sites = sites;
	}

}
