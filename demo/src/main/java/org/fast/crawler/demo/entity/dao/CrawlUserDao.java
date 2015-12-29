package org.fast.crawler.demo.entity.dao;

import org.fast.crawler.demo.entity.CrawlUser;
import org.fast.crawler.demo.entity.support.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xp017734 on 10/10/15.
 */
@Repository
public class CrawlUserDao extends BaseDao<CrawlUser> {

	public List<CrawlUser> queryBySite(String site) {
		String hql = "from CrawlUser where site = :site";
		return this.queryForList(hql, site);
	}

}
