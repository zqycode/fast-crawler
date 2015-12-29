package org.fast.crawler.demo.entity;

import org.fast.crawler.demo.entity.support.AbstractEntity;

/**
 * Created by xp017734 on 10/10/15.
 */
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crawl_user")
public class CrawlUser extends AbstractEntity {

	private String username;

	private String password;

	private String site;

	private Boolean enable = true;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
