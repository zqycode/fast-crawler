package org.fast.crawler.demo.entity;

import org.fast.crawler.demo.entity.support.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by xp017734 on 10/10/15.
 */
@Entity
@Table(name = "crawl_proxy")
public class CrawlProxy extends AbstractEntity {

	private String url;

	private String port;

	private String username;

	private String password;

	private Boolean socksProxy = false;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

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

	public Boolean getSocksProxy() {
		return socksProxy;
	}

	public void setSocksProxy(Boolean socksProxy) {
		this.socksProxy = socksProxy;
	}
}
