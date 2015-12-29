package org.fast.crawler.core.fetcher.proxy;

import org.apache.http.HttpHost;

public interface ProxyProducer {

	public HttpHost generateProxy();

}
