package org.fast.crawler.demo.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.fast.crawler.core.configuration.CrawlerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import org.fast.crawler.core.manager.CrawlerManager;
import org.fast.crawler.demo.services.QuartzConfigService;

@Configuration
@ComponentScan({ "com.sonymobile.hotpoints.crawl" })
@PropertySource(value = { "classpath:crawler.properties" })
public class QuartzConfiguration {

	@Autowired
	private Environment environment;

	@Autowired
	private QuartzConfigService quartzConfigService;

	@Bean
	@Autowired
	public SchedulerFactoryBean scheduler(DataSource d) {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setDataSource(d);
		schedulerFactoryBean.setAutoStartup(environment.getRequiredProperty("crawler.autoStartup", Boolean.class));
		schedulerFactoryBean.setOverwriteExistingJobs(environment.getProperty("crawler.overwriteExistingJobs", Boolean.class));

		Properties properties = new Properties();
		properties.put("org.quartz.scheduler.instanceName", environment.getRequiredProperty("crawler.scheduler.instanceName"));

		properties.put("org.quartz.threadPool.class", environment.getProperty("crawler.threadPool.class"));
		properties.put("org.quartz.threadPool.threadCount", environment.getProperty("crawler.threadPool.threadCount"));

		properties.put("org.quartz.jobStore.class", environment.getProperty("crawler.jobStore.class"));
		properties.put("org.quartz.jobStore.tablePrefix", environment.getProperty("crawler.jobStore.tablePrefix"));

		schedulerFactoryBean.setQuartzProperties(properties);
		return schedulerFactoryBean;
	}

	@Bean
	@Autowired
	public CrawlerConfig crawlConfig() {
		return quartzConfigService.loadConfig();
	}

	@Bean
	@Autowired
	public CrawlerManager crawlerManager(SchedulerFactoryBean s, CrawlerConfig c) {
		CrawlerManager crawlerManager = new CrawlerManager(s, c);

		Properties properties = new Properties();
		properties.put("crawler.cookies.storage.path", environment.getRequiredProperty("crawler.cookies.storage.path"));
		crawlerManager.setCrawlerProperties(properties);
		return crawlerManager;
	}

}
