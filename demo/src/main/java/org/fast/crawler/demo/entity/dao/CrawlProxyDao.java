package org.fast.crawler.demo.entity.dao;

import java.util.ArrayList;
import java.util.List;

import org.fast.crawler.demo.entity.support.BaseDao;
import org.fast.crawler.demo.entity.CrawlProxy;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by xp017734 on 10/10/15.
 */
@Repository
public class CrawlProxyDao extends BaseDao<CrawlProxy> {

	@SuppressWarnings("unchecked")
	public List<String> loadMatchIps(List<String> ips) {
		List<String> existIps = new ArrayList<String>();
		if (ips != null && !ips.isEmpty()) {
			String hql = "select c.url from CrawlProxy as c where c.url in (:ips)";
			Query query = this.getCurrentSession().createQuery(hql);
			query.setParameterList("ips", ips);
			existIps = query.list();
		}
		return existIps;
	}

}
