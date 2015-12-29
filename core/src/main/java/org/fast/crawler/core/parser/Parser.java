package org.fast.crawler.core.parser;

import org.fast.crawler.core.fetcher.response.ResponseWrapper;

public interface Parser {

	public void parse(ResponseWrapper<?> response);

}
