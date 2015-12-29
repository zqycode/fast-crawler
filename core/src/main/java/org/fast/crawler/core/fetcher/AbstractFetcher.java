package org.fast.crawler.core.fetcher;

import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.core.fetcher.response.ResponseWrapper;
import org.fast.crawler.core.auth.AuthProvider;
import org.fast.crawler.core.job.CrawlContext;
import org.fast.crawler.core.utils.HttpUtil;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;

import java.io.IOException;
import java.io.Serializable;

public abstract class AbstractFetcher implements Fetcher, Serializable {

	private static final long serialVersionUID = -1308513402508583213L;
	private CrawlerSiteConfig siteConfig = null;

	@Override
	public ResponseWrapper<?> startCrawl() {
		this.siteConfig = CrawlContext.getSiteConfig();
		ResponseWrapper<?> responseWrapper = null;
		try {
			RequestConfig config = this.configProxy();
			CookieStore cookieStore = this.configAuth();
			HttpResponse response = HttpUtil.get(siteConfig.getUrl(), null, config, cookieStore);
			responseWrapper = this.doCrawl(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseWrapper;
	}

	public abstract ResponseWrapper<?> doCrawl(HttpResponse response);

	private RequestConfig configProxy() {
		HttpHost proxy = null;
		if (siteConfig.getUseProxy()) {
			if (siteConfig.getProxyProducer() != null) {
				proxy = siteConfig.getProxyProducer().generateProxy();
			}
		}
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		return config;
	}

	private CookieStore configAuth() {
		CookieStore cookieStore = new BasicCookieStore();
		AuthProvider authProvider = siteConfig.getAuthProvider();
		if (authProvider != null) {
			cookieStore = authProvider.getCookies();
		}
		return cookieStore;
	}

}
