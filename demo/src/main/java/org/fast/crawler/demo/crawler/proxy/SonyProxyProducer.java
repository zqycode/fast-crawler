package org.fast.crawler.demo.crawler.proxy;

import org.apache.http.HttpHost;

import org.fast.crawler.core.fetcher.proxy.AbstractProxyProducer;

public class SonyProxyProducer extends AbstractProxyProducer {

	private static final long serialVersionUID = 4524091706749608114L;

	@Override
	public HttpHost generateProxy() {
		HttpHost proxy = new HttpHost("proxy.cnbj.sonyericsson.net", 8080);
		return proxy;
	}

}
