package org.fast.crawler.demo.entity.dao;

import java.util.ArrayList;
import java.util.List;

import org.fast.crawler.demo.entity.support.BaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import org.fast.crawler.demo.entity.CrawlResultDetail;

@Repository
public class CrawlResultDetailDao extends BaseDao<CrawlResultDetail> {

	@SuppressWarnings("unchecked")
	public List<Long> loadMatchIds(List<Long> ids) {
		List<Long> existIds = new ArrayList<Long>();
		if (ids != null && !ids.isEmpty()) {
			String hql = "select c.crawlItemId from CrawlResultDetail as c where c.crawlItemId in (:ids)";
			Query query = this.getCurrentSession().createQuery(hql);
			query.setParameterList("ids", ids);
			existIds = query.list();
		}
		return existIds;
	}
}
