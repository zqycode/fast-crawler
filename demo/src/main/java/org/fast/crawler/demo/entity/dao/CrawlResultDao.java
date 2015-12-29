package org.fast.crawler.demo.entity.dao;

import org.fast.crawler.demo.entity.CrawlResult;
import org.fast.crawler.demo.entity.support.BaseDao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xp017734 on 10/9/15.
 */
@Repository
public class CrawlResultDao extends BaseDao<CrawlResult> {

	@SuppressWarnings("unchecked")
	public List<Long> loadMatchIds(List<Long> ids) {
		List<Long> existIds = new ArrayList<Long>();
		if (ids != null && !ids.isEmpty()) {
			String hql = "select c.crawlItemId from CrawlResult as c where c.crawlItemId in (:ids)";
			Query query = this.getCurrentSession().createQuery(hql);
			query.setParameterList("ids", ids);
			existIds = query.list();
		}
		return existIds;
	}

}
