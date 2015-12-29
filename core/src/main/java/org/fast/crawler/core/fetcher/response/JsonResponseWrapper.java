package org.fast.crawler.core.fetcher.response;

import org.fast.crawler.core.utils.JsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class JsonResponseWrapper implements ResponseWrapper<Map<String, Object>> {

	private HttpResponse response;

	private String content;

	public JsonResponseWrapper(HttpResponse response) {
		this.response = response;
		HttpEntity resEntity = response.getEntity();
		try {
			this.content = EntityUtils.toString(resEntity);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getResponse() {
		return JsonUtil.toMap(this.content);
	}

	@Override
	public Document toDocument() {
		return null;
	}
}
