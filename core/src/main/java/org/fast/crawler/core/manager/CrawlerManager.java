package org.fast.crawler.core.manager;

import org.fast.crawler.core.configuration.CrawlerConfig;
import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.core.job.CrawlJob;
import org.fast.crawler.core.message.MessageBus;
import org.fast.crawler.core.message.SubCrawlListener;

import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals;

public class CrawlerManager {

	private Logger logger = LoggerFactory.getLogger(CrawlerManager.class);

	private SchedulerFactoryBean sf;

	private Scheduler scheduler = null;

	private boolean initStatus = false;

	private CrawlerConfig crawlerConfig;

	private Properties crawlerProperties;

	public CrawlerManager(SchedulerFactoryBean sf, CrawlerConfig crawlerConfig) {
		logger.info("init to crawler");
		this.sf = sf;
		this.crawlerConfig = crawlerConfig;

		MessageBus.getInstance().registListener(MessageBus.SUB_CRAWL_SITE_MSG, new SubCrawlListener(this));

		this.initStatus = true;
	}

	public void start() {
		if (!this.initStatus)
			throw new IllegalStateException("dont init crwaler");
		logger.info("clear jobs");
		try {
			this.scheduler = sf.getScheduler();
			this.clearJobs(this.crawlerConfig.getSites());

			logger.info("start to crawler");
			if (this.crawlerConfig.getSites() != null) {
				for (CrawlerSiteConfig siteConfig : this.crawlerConfig.getSites()) {
					MessageBus.getInstance().addMessage(MessageBus.SUB_CRAWL_SITE_MSG, siteConfig);
				}
			}
			this.scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	private void clearJobs(List<CrawlerSiteConfig> siteConfigs) {
		for (CrawlerSiteConfig siteConfig : siteConfigs) {
			String groupName = "group-" + siteConfig.getName();
			try {
				List<JobKey> keys = newArrayList(this.scheduler.getJobKeys(jobGroupEquals(groupName)));
				this.scheduler.deleteJobs(keys);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	private List<JobKey> newArrayList(Set<JobKey> keys) {
		List<JobKey> keyList = new ArrayList<JobKey>();
		for (JobKey key : keys) {
			keyList.add(key);
		}
		return keyList;
	}

	/**
	 *
	 * 添加新的任务
	 *
	 * @param siteConfig
	 */
	public void addSiteJob(CrawlerSiteConfig siteConfig) {
		if (this.scheduler == null) {
			throw new RuntimeException("not start scheduler!");
		}
		UUID uuid = UUID.randomUUID();
		;
		String jobKey = siteConfig.getName() + "-" + uuid;
		JobDetail job = newJob(CrawlJob.class).withIdentity(jobKey, "group-" + siteConfig.getName()).build();
		job.getJobDataMap().put("SITE-CONFIG", siteConfig);

		Trigger trigger;
		if (StringUtils.isNotEmpty(siteConfig.getCronExp())) {
			trigger = newTrigger().withIdentity("trigger-" + jobKey, "group-" + siteConfig.getName()).withSchedule(cronSchedule(siteConfig.getCronExp())).build();
		} else if (siteConfig.getStartTime() != null) {
			trigger = newTrigger().withIdentity("trigger-" + jobKey, "group-" + siteConfig.getName()).startAt(siteConfig.getStartTime()).build();
		} else {
			trigger = newTrigger().withIdentity("trigger-" + jobKey, "group-" + siteConfig.getName()).startNow().build();
		}

		try {
			this.scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public String getStatus() {
		try {
			boolean isShutdown = this.scheduler == null || this.scheduler.isShutdown();
			if (isShutdown)
				return "shutdown";
			boolean isStarted = this.scheduler.isStarted();
			if (isStarted)
				return "started";
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void end() {
		try {
			MessageBus.getInstance().clearMessage();
			if (this.scheduler != null) {
				this.scheduler.shutdown(true);
				this.scheduler = null;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public Properties getCrawlerProperties() {
		return crawlerProperties;
	}

	public void setCrawlerProperties(Properties crawlerProperties) {
		this.crawlerProperties = crawlerProperties;
	}

	public String getCrawlerProperty(String key) {
		return this.crawlerProperties.getProperty(key);
	}

	public CrawlerConfig getCrawlerConfig() {
		return crawlerConfig;
	}

	public void setCrawlerConfig(CrawlerConfig crawlerConfig) {
		this.crawlerConfig = crawlerConfig;
	}

}
