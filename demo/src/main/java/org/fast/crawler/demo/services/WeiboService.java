package org.fast.crawler.demo.services;

import org.fast.crawler.demo.entity.CrawlResult;
import org.fast.crawler.demo.entity.dao.CrawlResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xp017734 on 10/12/15.
 */
@Service
public class WeiboService {

    @Autowired
    private CrawlResultDao crawlResultDao;

    @Transactional
    public void addCrawlResults(List<CrawlResult> results) {
        List<Long> ids = new ArrayList<Long>();
        for (CrawlResult result : results) {
            ids.add(result.getCrawlItemId());
        }
        List<Long> existIds = crawlResultDao.loadMatchIds(ids);
        Iterator<CrawlResult> it = results.iterator();
        while(it.hasNext()) {
            CrawlResult crawlResult = it.next();
            if(existIds.contains(crawlResult.getCrawlItemId())) {
                it.remove();
            }
        }
        crawlResultDao.batchSave(results);
    }

}
