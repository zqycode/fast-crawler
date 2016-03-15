#微博网页自动抓取项目

[![Build Status](https://travis-ci.org/rapid-develop/fast-crawler.svg?branch=master)](https://travis-ci.org/chenrui1988/fast-crawler)

>本项目实现自动抓取微博和网页数据，支持微博认证，代理自动切换等功能。该抓取只支持定向的精准的抓取，不支持二级三级页面的抓取。

**本项目主要分为两个模块**
- core 抓取核心功能，实现抓取的任务调度
- demo 抓取示例，展示如何抓取微博和网页数据


**core 依赖框架：**
- spring-4.2.1.RELEASE
- quartz-2.2.1
- jsoup-1.7.2
- httpclient-4.3.2


**core 抓取使用示例：**

1. Clone this project 
``` bash
git clone https://github.com/chenrui1988/fast-crawler.git
```

2. build this project 
``` bash
cd fast-crawler
mvn clean install
```

3. 引用crawl-core 包，目前版本1.0-SNAPSHOT
``` xml
<dependency>
    <groupId>org.fast.crawler</groupId>
    <artifactId>core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
使用Spring 启动 CrawlerManager
``` java
@Bean
@Autowired
public CrawlerManager crawlerManager(SchedulerFactoryBean s, CrawlerConfig c) {
	CrawlerManager crawlerManager = new CrawlerManager(s, c);

	Properties properties = new Properties();
	crawlerManager.setCrawlerProperties(properties);
	return crawlerManager;
}

@Bean
@Autowired
public CrawlerConfig crawlConfig() {
	CrawlerConfig crawlerConfig = new CrawlerCofig();
	
	CrawlerSiteConfig siteConfig = new CrawlerSiteConfig();
	siteConfig.setName("weibo");
	siteConfig.setCronExp("0 0/1 * * * ?");
	siteConfig.setUrl("http://s.weibo.com/weibo/iphone");
	siteConfig.setParser(new WeiboPraser()); //解析抓取微博数据并后续处理
	siteConfig.setAuthProvider(new SinaAuthProvider()); //提供认证数据
	
	crawlerConfig.addSite(siteConfig);
	return crawlerConfig
}
```
动态添加抓取任务
``` java
CrawlerSiteConfig siteConfig = new CrawlerSiteConfig();
siteConfig.setName("toutiao");
siteConfig.setCronExp("0 0/1 * * * ?");
siteConfig.setUrl("http://toutiao.com/search_content/?offset=0&format=json&keyword=iphone&autoload=true&count=20");
siteConfig.setParser(new ToutiaoPraser());

crawlerManager.addSiteJob(siteConfig);
``` 
抓取内容解析
``` java
public class WeiboParser extends AbstractParser {
	public void parse(ResponseWrapper<?> response) {
	    // response 返回数据
	    // Document 使用JSOUP API 解析HTML
		Document document = response.toDocument();
	}
}
``` 
自定义认证
``` java
public class SinaAuthProvider extends AbstractAuthProvider {
	public CookieStore doAuth()  {
		// 认证并提供认证所需的cookie
	}

	public User loadUser() {
		// 认证所需的用户
	}
}
```  
