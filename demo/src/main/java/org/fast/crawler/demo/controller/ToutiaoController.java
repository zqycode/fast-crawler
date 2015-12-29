package org.fast.crawler.demo.controller;

import org.fast.crawler.demo.model.PageData;
import org.fast.crawler.demo.model.ProcessResult;
import org.fast.crawler.demo.entity.CrawlResult;
import org.fast.crawler.demo.services.ToutiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xp017734 on 10/10/15.
 */
@Controller
@RequestMapping("/toutiao")
public class ToutiaoController {

	@Autowired
	ToutiaoService toutiaoService;

	@RequestMapping("/search")
	@ResponseBody
	public ProcessResult search(@RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "20") Integer size) {
		PageData<CrawlResult> page = toutiaoService.findCrawlResult(offset, size);
		ProcessResult processResult = new ProcessResult(page);
		return processResult;
	}

}
