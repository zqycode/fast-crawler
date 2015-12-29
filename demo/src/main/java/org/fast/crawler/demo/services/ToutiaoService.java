package org.fast.crawler.demo.services;

/**
 * Created by xp017734 on 10/9/15.
 */

import org.fast.crawler.demo.entity.CrawlResult;
import org.fast.crawler.demo.entity.dao.CrawlResultDao;
import org.fast.crawler.demo.model.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ToutiaoService {

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


    public List<CrawlResult> findAllCrawlResult() {
        String hql = "from CrawlResult where media = 'toutiao'";
        List<CrawlResult> crawlResults = crawlResultDao.queryForList(hql);
        return crawlResults;
    }

    public PageData<CrawlResult> findCrawlResult(Integer offset, Integer size) {
        String hql = "from CrawlResult where media = 'toutiao' order by id desc";
        List<CrawlResult> crawlResults = crawlResultDao.queryForPage(hql, offset, size);
        String counthql = "select count(id) from CrawlResult where media = 'toutiao'";
        Long total = (Long) crawlResultDao.queryForObject(counthql);

        PageData<CrawlResult> pageData = new PageData<CrawlResult>();
        pageData.setCount(crawlResults.size());
        pageData.setTotal(total);
        pageData.setDatas(crawlResults);
        return pageData;
    }
}
