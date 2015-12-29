package org.fast.crawler.demo.crawler.toutiao;

import org.fast.crawler.core.configuration.CrawlerSiteConfig;
import org.fast.crawler.demo.entity.CrawlResult;
import org.fast.crawler.core.fetcher.response.ResponseWrapper;
import org.fast.crawler.core.job.CrawlContext;
import org.fast.crawler.core.parser.AbstractParser;
import org.fast.crawler.core.utils.JsonUtil;
import org.fast.crawler.core.utils.SpringUtil;
import org.fast.crawler.demo.services.ToutiaoService;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TouTiaoParser extends AbstractParser {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked" })
	public void parse(ResponseWrapper<?> response) {
		String jsonData = response.getResponse().toString();
		if (StringUtils.isNotEmpty(jsonData)) {
			Map<String, Object> data = JsonUtil.toMap(jsonData);
			List<CrawlResult> results = new ArrayList<CrawlResult>();
			List<Map<String, Object>> articles = (List<Map<String, Object>>) data.get("data");
			for (Map<String, Object> article : articles) {
				CrawlResult crawlResult = new CrawlResult();
				crawlResult.setTitle(toString(article.get("title")));
				crawlResult.setHasImage(toBoolean(article.get("has_image")));
				List<LinkedHashMap<String, String>> images = (List<LinkedHashMap<String, String>>) article.get("image_list");
				for (LinkedHashMap<String, String> map : images) {
					crawlResult.addImage(toString(map.get("url")));
				}
				String imgUrl = toString(article.get("image_url"));
				if (StringUtils.isNotEmpty(imgUrl)) {
					crawlResult.addImage(imgUrl);
				}

				Long crawlItemId = toLong(article.get("id"));
				crawlResult.setSummary(toString(article.get("abstract")));
				crawlResult.setCommentCount(toInteger(article.get("comments_count")));
				crawlResult.setCrawlItemId(crawlItemId);
				crawlResult.setMedia("toutiao");
				crawlResult.setSource(toString(article.get("source")));
				crawlResult.setAuthor(toString(article.get("source")));
				crawlResult.setCreateTime(toLong(article.get("create_time")));
				crawlResult.setPublicTime(toLong(article.get("publish_time")));
				crawlResult.setUnlikeNum(toInteger(article.get("bury_count")));
				crawlResult.setLikeNum(toInteger(article.get("digg_count")));
				results.add(crawlResult);

				// sub crawl
				if (!CrawlItemIdCache.contains(crawlItemId)) {
					String subUrl = "http://toutiao.com" + toString(article.get("seo_url"));
					CrawlerSiteConfig siteConfig = CrawlContext.getSiteConfig().clone();

					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					c.add(Calendar.MINUTE, 1);

					siteConfig.setName(siteConfig.getName() + "-" + crawlItemId);
					siteConfig.setCronExp(null);
					siteConfig.setStartTime(c.getTime());
					siteConfig.setUrl(subUrl);
					siteConfig.setParser(new ToutiaoDetailParser(crawlItemId));

					this.notifySubCrawlMessage(siteConfig);
				}
			}

			ToutiaoService toutiaoService = SpringUtil.getBean(ToutiaoService.class);
			toutiaoService.addCrawlResults(results);
		}
	}

}
