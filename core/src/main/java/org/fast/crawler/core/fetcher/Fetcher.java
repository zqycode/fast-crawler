package org.fast.crawler.core.fetcher;

import org.fast.crawler.core.fetcher.response.ResponseWrapper;

public interface Fetcher {

	public ResponseWrapper<?> startCrawl();

}
