package org.fast.crawler.demo.crawler.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.fast.crawler.demo.entity.CrawlProxy;
import org.fast.crawler.core.fetcher.response.ResponseWrapper;
import org.fast.crawler.core.parser.AbstractParser;
import org.fast.crawler.demo.services.ProxyService;
import org.fast.crawler.core.utils.HttpUtil;
import org.fast.crawler.core.utils.SpringUtil;

public class ProxyPraser extends AbstractParser {

	private static final long serialVersionUID = 1L;

	public void parse(ResponseWrapper<?> response) {

		Document document = response.toDocument();
		Element table = document.getElementById("ip_list");
		if (table != null) {
			Elements trs = table.getElementsByTag("tr");
			Iterator<Element> it = trs.iterator();
			it.next();

			List<CrawlProxy> proxys = new ArrayList<CrawlProxy>();
			while (it.hasNext()) {
				Element tr = it.next();
				String ip = tr.child(2).html();
				String port = tr.child(3).html();
				String protocol = tr.child(6).html();

				if (protocol.equals("HTTP")) {
					if (testHttp(ip, toInteger(port))) {
						CrawlProxy proxy = new CrawlProxy();
						proxy.setUrl(ip);
						proxy.setPort(port);
						proxys.add(proxy);
					}
				}
			}

			ProxyService proxyService = SpringUtil.getBean(ProxyService.class);
			proxyService.addCrawlProxys(proxys);
		}

	}

	public boolean testHttp(String ip, Integer port) {
		boolean work = false;
		HttpHost proxy = new HttpHost(ip, port);
		RequestConfig config = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).setConnectionRequestTimeout(2000).setProxy(proxy).build();
		try {
			HttpUtil.get("http://baidu.com", null, config, null);
		} catch (IOException e) {
			work = false;
		}
		return work;
	}

}
