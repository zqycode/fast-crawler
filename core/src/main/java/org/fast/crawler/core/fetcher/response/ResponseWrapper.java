package org.fast.crawler.core.fetcher.response;

import org.jsoup.nodes.Document;

public interface ResponseWrapper<T> {

	public T getResponse();

	public Document toDocument();

}
