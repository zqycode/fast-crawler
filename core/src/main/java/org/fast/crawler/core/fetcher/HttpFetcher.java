package org.fast.crawler.core.fetcher;

import org.fast.crawler.core.fetcher.response.ResponseWrapper;
import org.fast.crawler.core.fetcher.response.HttpResponseWrapper;
import org.apache.http.HttpResponse;

public class HttpFetcher extends AbstractFetcher {

	private static final long serialVersionUID = 2526852462415125760L;

	@Override
	public ResponseWrapper<?> doCrawl(HttpResponse response) {
		ResponseWrapper<?> responseWrapper = new HttpResponseWrapper(response);
		return responseWrapper;
	}

}
