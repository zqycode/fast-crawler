package org.fast.crawler.demo.entity;

import org.fast.crawler.demo.entity.support.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crawl_site_config")
public class CrawlSiteConfig extends AbstractEntity {

	private String name;

	private String url;

	private String parser;

	private boolean useProxy;

	private String proxyProducer;

	private String authProvider;

	private String userTag;

	private String cronExp;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParser() {
		return parser;
	}

	public void setParser(String parser) {
		this.parser = parser;
	}

	public boolean isUseProxy() {
		return useProxy;
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

	public String getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(String authProvider) {
		this.authProvider = authProvider;
	}

	public String getUserTag() {
		return userTag;
	}

	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}

	public String getCronExp() {
		return cronExp;
	}

	public void setCronExp(String cronExp) {
		this.cronExp = cronExp;
	}

	public String getProxyProducer() {
		return proxyProducer;
	}

	public void setProxyProducer(String proxyProducer) {
		this.proxyProducer = proxyProducer;
	}

}
