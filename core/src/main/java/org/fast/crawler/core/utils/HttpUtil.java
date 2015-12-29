package org.fast.crawler.core.utils;

import java.io.IOException;
import java.util.List;

import org.fast.crawler.core.manager.Constants;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpUtil {

	public static HttpResponse get(String url, List<NameValuePair> params, RequestConfig config, CookieStore cookieStore) throws IOException {
		CloseableHttpClient client = HttpClients.custom().setUserAgent(Constants.DEFAULT_USER_AGENT).build();
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

		String reqUrl = "";
		if (params != null && params.size() > 0) {
			reqUrl = url + "?" + URLEncodedUtils.format(params, "utf-8");
		} else {
			reqUrl = url;
		}
		HttpGet get = new HttpGet(reqUrl);
		get.setConfig(config);
		HttpResponse response = client.execute(get, localContext);
		return response;
	}

	public static HttpResponse post(String url, List<NameValuePair> params, RequestConfig config, CookieStore cookieStore) throws IOException {
		CloseableHttpClient client = HttpClients.custom().setUserAgent(Constants.DEFAULT_USER_AGENT).build();
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params));
		post.setConfig(config);
		HttpResponse response = client.execute(post, localContext);
		return response;
	}

	public static void main(String[] args) {
		HttpHost proxy = new HttpHost("210.74.130.34", 8080);
		RequestConfig config = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).setConnectionRequestTimeout(2000).setProxy(proxy).build();
		try {
			HttpResponse response = HttpUtil.get("http://www.baidu.com", null, config, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
