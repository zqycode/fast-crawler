package org.fast.crawler.core.fetcher.response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HttpResponseWrapper implements ResponseWrapper<String> {

	private HttpResponse response;

	private String body = "";

	private Document document;

	public HttpResponseWrapper(HttpResponse response) {
		this.response = response;
		HttpEntity resEntity = response.getEntity();
		try {
			this.body = EntityUtils.toString(resEntity);
			this.document = Jsoup.parse(this.body);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getResponse() {
		return this.body;
	}

	@Override
	public Document toDocument() {
		return this.document;
	}

}
