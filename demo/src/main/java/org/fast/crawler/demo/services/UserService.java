package org.fast.crawler.demo.services;

import org.fast.crawler.demo.entity.CrawlUser;
import org.fast.crawler.demo.entity.dao.CrawlUserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xp017734 on 10/12/15.
 */
@Service
public class UserService {

    @Autowired
    CrawlUserDao crawlUserDao;

    public List<CrawlUser> findUsers(String site) {
        List<CrawlUser> crawlUsers;
        if(StringUtils.isEmpty(site)) {
            crawlUsers = crawlUserDao.queryAll();
        } else {
            crawlUsers = crawlUserDao.queryBySite(site);
        }
        return crawlUsers;
    }
}
