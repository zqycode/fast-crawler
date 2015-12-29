package org.fast.crawler.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fast.crawler.core.configuration.CrawlerConfig;
import org.fast.crawler.core.manager.CrawlerManager;
import org.fast.crawler.demo.model.ProcessResult;
import org.fast.crawler.demo.model.ScheduleJob;
import org.fast.crawler.demo.services.QuartzConfigService;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xp017734 on 10/12/15.
 */
@Controller
@RequestMapping("/job")
public class JobController {

	@Autowired
	QuartzConfigService quartzConfigService;

	@Autowired
	CrawlerManager crawlerManager;

	@RequestMapping("/reset")
	@ResponseBody
	public ProcessResult reset() {
		crawlerManager.end();

		CrawlerConfig config = quartzConfigService.loadConfig();
		crawlerManager.setCrawlerConfig(config);
		crawlerManager.start();
		ProcessResult processResult = new ProcessResult();
		return processResult;
	}

	@RequestMapping("/shutdown")
	@ResponseBody
	public ProcessResult shutdown() {
		crawlerManager.end();
		ProcessResult processResult = new ProcessResult();
		return processResult;
	}

	@RequestMapping("/status")
	@ResponseBody
	public ProcessResult status() {
		String status = crawlerManager.getStatus();

		ProcessResult processResult = new ProcessResult(status);
		return processResult;
	}

	@RequestMapping("/monitor/execution")
	@ResponseBody
	public ProcessResult monitorExecution() throws SchedulerException {
		Scheduler scheduler = crawlerManager.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDesc("触发器:" + trigger.getKey());
			if (executingJob.getNextFireTime() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm");
				job.setNextFireTime(dateFormat.format(executingJob.getNextFireTime()));
			}
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		ProcessResult processResult = new ProcessResult(jobList);
		return processResult;
	}

	@RequestMapping("/monitor/plan")
	@ResponseBody
	public ProcessResult monitorPlan() throws SchedulerException {
		Scheduler scheduler = crawlerManager.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDesc("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				if (trigger.getNextFireTime() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm");
					job.setNextFireTime(dateFormat.format(trigger.getNextFireTime()));
				}
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		ProcessResult processResult = new ProcessResult(jobList);
		return processResult;
	}

	@RequestMapping("/pausejob/{groupkey}/{jobkey}")
	@ResponseBody
	public ProcessResult stopJob(@PathVariable String groupkey, @PathVariable String jobkey) throws SchedulerException {
		Scheduler scheduler = crawlerManager.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobkey, groupkey);
		scheduler.pauseJob(jobKey);
		ProcessResult processResult = new ProcessResult();
		return processResult;
	}

	@RequestMapping("/resumejob/{groupkey}/{jobkey}")
	@ResponseBody
	public ProcessResult resumeJob(@PathVariable String groupkey, @PathVariable String jobkey) throws SchedulerException {
		Scheduler scheduler = crawlerManager.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobkey, groupkey);
		scheduler.resumeJob(jobKey);
		ProcessResult processResult = new ProcessResult();
		return processResult;
	}

	@RequestMapping("/deletejob/{groupkey}/{jobkey}")
	@ResponseBody
	public ProcessResult deleteJob(@PathVariable String groupkey, @PathVariable String jobkey) throws SchedulerException {
		Scheduler scheduler = crawlerManager.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobkey, groupkey);
		scheduler.deleteJob(jobKey);
		ProcessResult processResult = new ProcessResult();
		return processResult;
	}

	@RequestMapping("/runnow/{groupkey}/{jobkey}")
	@ResponseBody
	public ProcessResult runNow(@PathVariable String groupkey, @PathVariable String jobkey) throws SchedulerException {
		Scheduler scheduler = crawlerManager.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobkey, groupkey);
		scheduler.triggerJob(jobKey);
		ProcessResult processResult = new ProcessResult();
		return processResult;
	}

}
