package org.fast.crawler.demo.entity;

import org.fast.crawler.demo.entity.support.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by xp017734 on 10/10/15.
 */
@Entity
@Table(name = "crawl_keywords")
public class CrawlKeyWord extends AbstractEntity {

	private String value;

	private Boolean deleted = false;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
