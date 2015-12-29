package org.fast.crawler.demo.services;

import org.fast.crawler.demo.entity.dao.CrawlKeyWordDao;
import org.fast.crawler.demo.entity.CrawlKeyWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xp017734 on 10/12/15.
 */
@Service
public class KeywordService {

    @Autowired
    CrawlKeyWordDao crawlKeyWordDao;

    public List<String> findAllKeywords() {
        List<String> keywords = new ArrayList<String>();
        List<CrawlKeyWord> crawlKeyWords = crawlKeyWordDao.queryAll();
        for(CrawlKeyWord crawlKeyWord : crawlKeyWords) {
            keywords.add(crawlKeyWord.getValue());
        }
        return keywords;
    }

    @Transactional
    public void saveKeywords(List<String> keywords) {
        crawlKeyWordDao.deleteAll();
        for(String keyword : keywords) {
            CrawlKeyWord crawlKeyWord = new CrawlKeyWord();
            crawlKeyWord.setValue(keyword);
            crawlKeyWordDao.save(crawlKeyWord);
        }
    }
}
