package org.fast.crawler.demo.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fast.crawler.demo.entity.CrawlProxy;
import org.fast.crawler.demo.entity.dao.CrawlProxyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProxyService {
	
	@Autowired
    private CrawlProxyDao crawlProxyDao;

    @Transactional
    public void addCrawlProxys(List<CrawlProxy> results) {
        List<String> ips = new ArrayList<String>();
        for (CrawlProxy proxy : results) {
        	ips.add(proxy.getUrl());
        }
        List<String> existIds = crawlProxyDao.loadMatchIps(ips);
        Iterator<CrawlProxy> it = results.iterator();
        while(it.hasNext()) {
        	CrawlProxy proxy = it.next();
            if(existIds.contains(proxy.getUrl())) {
                it.remove();
            }
        }
        crawlProxyDao.batchSave(results);
    }

}
