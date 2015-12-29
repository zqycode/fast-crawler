package org.fast.crawler.demo.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fast.crawler.demo.entity.dao.CrawlResultDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.fast.crawler.demo.entity.CrawlResultDetail;

@Service
public class CrawlResultDetailService {
	
	@Autowired
    CrawlResultDetailDao crawlResultDetailDao;
	
	@Transactional
    public void addCrawlResultDetails(List<CrawlResultDetail> results) {
        List<Long> ids = new ArrayList<Long>();
        for (CrawlResultDetail detail : results) {
        	ids.add(detail.getCrawlItemId());
        }
        List<Long> existIds = crawlResultDetailDao.loadMatchIds(ids);
        Iterator<CrawlResultDetail> it = results.iterator();
        while(it.hasNext()) {
        	CrawlResultDetail detail = it.next();
            if(existIds.contains(detail.getCrawlItemId())) {
                it.remove();
            }
        }
        crawlResultDetailDao.batchSave(results);
    }
	
}
