package org.fast.crawler.demo.controller;

import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.demo.crawler.proxy.ProxyPraser;
import org.fast.crawler.demo.entity.CrawlUser;
import org.fast.crawler.demo.crawler.proxy.SonyProxyProducer;
import org.fast.crawler.core.manager.CrawlerManager;
import org.fast.crawler.demo.model.ProcessResult;
import org.fast.crawler.demo.services.KeywordService;
import org.fast.crawler.demo.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ConfigController {

	@Autowired
	KeywordService keywordService;

	@Autowired
	UserService userService;

	@Autowired
	CrawlerManager crawlerManager;

	@RequestMapping("/keyword/list")
	@ResponseBody
	public ProcessResult listAllKeyword() {
		List<String> keywords = keywordService.findAllKeywords();
		ProcessResult processResult = new ProcessResult(keywords);
		return processResult;
	}

	@RequestMapping(path = "/keyword/save", method = RequestMethod.POST)
	@ResponseBody
	public ProcessResult saveKeywords(@RequestBody List<String> keywords) {
		ProcessResult processResult = new ProcessResult();
		keywordService.saveKeywords(keywords);
		return processResult;
	}

	@RequestMapping("/user/list")
	@ResponseBody
	public ProcessResult listAllUser(@RequestParam(required = false) String site) {
		List<CrawlUser> crawlUsers = userService.findUsers(site);
		ProcessResult processResult = new ProcessResult(crawlUsers);
		return processResult;
	}

	@RequestMapping("/proxy/init")
	@ResponseBody
	public ProcessResult initProxy() {
		for (int i = 1; i < 30; i++) {
			CrawlerSiteConfig siteConfig = new CrawlerSiteConfig();

			String url = "http://www.kuaidaili.com/proxylist/" + i;
			siteConfig.setName("proxy");
			siteConfig.setUrl(url);
			siteConfig.setUseProxy(true);
			siteConfig.setParser(new ProxyPraser());
			siteConfig.setProxyProducer(new SonyProxyProducer());

			crawlerManager.addSiteJob(siteConfig);
		}
		ProcessResult processResult = new ProcessResult("init proxy success!");
		return processResult;
	}

}
