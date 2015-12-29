package org.fast.crawler.demo.entity.support;

import java.util.List;

import org.fast.crawler.core.utils.Reflections;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDao<T extends AbstractEntity> {

	@SuppressWarnings("unchecked")
	private Class<T> entityClass = Reflections.getSuperClassGenricType(this.getClass());

	@Autowired
	SessionFactory sessionFactory;

	public void save(T t) {
		this.getCurrentSession().save(t);
	}

	public void batchSave(List<T> beans) {
		Session session = this.getCurrentSession();
		for (int i = 0; i < beans.size(); i++) {
			session.save(beans.get(i));
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	public void persist(T t) {
		this.getCurrentSession().persist(t);
	}

	public void update(T t) {
		this.getCurrentSession().update(t);
	}

	public void merge(T t) {
		this.getCurrentSession().merge(t);
	}

	public T findById(Long id) {
		return (T) this.getCurrentSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByIds(List<Long> ids) {
		String hql = "from " + entityClass.getSimpleName() + " where id in :ids";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> queryAll() {
		String hql = "from " + entityClass.getSimpleName();
		Query query = this.getCurrentSession().createQuery(hql);
		return query.list();
	}

	public Object queryForObject(String hql, Object... args) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (args != null && args.length > 0) {
			int i = 0;
			for (Object arg : args) {
				query.setParameter(i, arg);
				i++;
			}
		}
		return query.uniqueResult();
	}

	public void execute(String hql, Object... args) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (args != null) {
			int i = 0;
			for (Object arg : args) {
				query.setParameter(i++, arg);
			}
		}
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<T> queryForList(String hql, Object... args) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (args != null) {
			int i = 0;
			for (Object arg : args) {
				query.setParameter(i++, arg);
			}
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> queryForPage(String hql, Integer start, Integer length, Object... args) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (args != null) {
			int i = 0;
			for (Object arg : args) {
				query.setParameter(i++, arg);
			}
		}
		query.setFirstResult(start);
		query.setMaxResults(length);
		return query.list();
	}

	public void delete(T t) {
		this.getCurrentSession().delete(t);
	}

	public void deleteAll() {
		String hql = "delete " + entityClass.getSimpleName();
		this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	public void delete(Long id) {
		String hql = "delete " + entityClass.getSimpleName() + " where id = :id";
		this.getCurrentSession().createQuery(hql).setParameter("id", id).executeUpdate();
	}

	protected Session getCurrentSession() {
		Session session = null;
		try {
			session = this.sessionFactory.getCurrentSession();
		} catch (HibernateException ex) {
			session = this.sessionFactory.openSession();
		}
		return session;
	}

}