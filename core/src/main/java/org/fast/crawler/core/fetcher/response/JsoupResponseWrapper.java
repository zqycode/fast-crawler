package org.fast.crawler.core.fetcher.response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupResponseWrapper implements ResponseWrapper<String> {

	private String content;

	private Document document;

	public JsoupResponseWrapper(HttpResponse response) {
		HttpEntity resEntity = response.getEntity();
		try {
			this.content = EntityUtils.toString(resEntity);
			this.document = Jsoup.parse(this.content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getResponse() {
		return content;
	}

	public Document toDocument() {
		return this.document;
	}

}
