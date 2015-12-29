package org.fast.crawler.demo.crawler.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpHost;

import org.fast.crawler.demo.entity.CrawlProxy;
import org.fast.crawler.demo.entity.dao.CrawlProxyDao;
import org.fast.crawler.core.utils.SpringUtil;
import org.fast.crawler.core.fetcher.proxy.AbstractProxyProducer;

public class RandomProxyProducer extends AbstractProxyProducer {

	private static final long serialVersionUID = 3513665947215603685L;
	private static List<CrawlProxy> proxys = new ArrayList<CrawlProxy>();

	@Override
	public HttpHost generateProxy() {
		if (proxys.size() == 0) {
			CrawlProxyDao crawlProxyDao = SpringUtil.getBean(CrawlProxyDao.class);
			proxys = crawlProxyDao.queryAll();
		}
		CrawlProxy crawlProxy = proxys.get(randomInt(proxys.size()));

		HttpHost proxy = new HttpHost(crawlProxy.getUrl(), Integer.parseInt(crawlProxy.getPort()));
		return proxy;
	}

	private Integer randomInt(Integer max) {
		Random random = new Random(max);
		Integer i = random.nextInt(max);
		return i;
	}

}
