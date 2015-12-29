package org.fast.crawler.core.configuration;

import org.fast.crawler.core.parser.Parser;
import org.fast.crawler.core.auth.AuthProvider;
import org.fast.crawler.core.fetcher.proxy.ProxyProducer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class CrawlerSiteConfig implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String url;

	private Boolean needDepth;

	private Parser parser;

	private Boolean useProxy = false;

	private ProxyProducer proxyProducer;

	private AuthProvider authProvider;

	private String cronExp;

	private Date startTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getNeedDepth() {
		return needDepth;
	}

	public void setNeedDepth(Boolean needDepth) {
		this.needDepth = needDepth;
	}

	public Boolean getUseProxy() {
		return useProxy;
	}

	public void setUseProxy(Boolean useProxy) {
		this.useProxy = useProxy;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public ProxyProducer getProxyProducer() {
		return proxyProducer;
	}

	public void setProxyProducer(ProxyProducer proxyProducer) {
		this.proxyProducer = proxyProducer;
	}

	public AuthProvider getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(AuthProvider authProvider) {
		this.authProvider = authProvider;
	}

	public String getCronExp() {
		return cronExp;
	}

	public void setCronExp(String cronExp) {
		this.cronExp = cronExp;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public CrawlerSiteConfig clone() {
		try {
			// 将对象写到流里
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(this);
			// 从流里读出来
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			return (CrawlerSiteConfig) (oi.readObject());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
