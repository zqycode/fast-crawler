package org.fast.crawler.demo.crawler.toutiao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.fast.crawler.core.fetcher.response.ResponseWrapper;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.fast.crawler.demo.entity.CrawlResultDetail;
import org.fast.crawler.core.parser.AbstractParser;
import org.fast.crawler.demo.services.CrawlResultDetailService;
import org.fast.crawler.core.utils.SpringUtil;

public class ToutiaoDetailParser extends AbstractParser {

	private static final long serialVersionUID = 1L;
	private Long crawlItemId;

	ToutiaoDetailParser(Long crawlItemId) {
		this.crawlItemId = crawlItemId;
	}

	@Override
	public void parse(ResponseWrapper<?> response) {
		Document document = response.toDocument();
		Elements element = document.getElementsByClass("article-detail");

		List<CrawlResultDetail> results = new ArrayList<CrawlResultDetail>();
		if (element != null) {
			CrawlResultDetail crawlResultDetail = new CrawlResultDetail();

			Elements titleEles = document.select(".title h1");
			if (titleEles.size() > 0) {
				String title = toString(titleEles.get(0).html());
				crawlResultDetail.setTitle(title);
			}

			Elements timeELes = document.select(".title .time");
			if (timeELes.size() > 0) {
				String time = toString(timeELes.get(0).html());
				crawlResultDetail.setTime(time);
			}

			Elements articleContentELes = document.select(".article-content");
			if (articleContentELes.size() > 0) {
				String articleContent = toString(articleContentELes.get(0).html());
				crawlResultDetail.setArticleDetail(articleContent);
			}

			if (document.getElementById("pagelet-comment") != null) {
				Elements commentsELes = document.getElementById("pagelet-comment").select("cc");
				if (commentsELes.size() > 0) {
					Integer comments = toInteger(toString(commentsELes.get(0).html()).replaceAll("条评论", ""));
					crawlResultDetail.setCommentNum(comments);
				}
			}

			crawlResultDetail.setCrawlItemId(this.crawlItemId);
			crawlResultDetail.setCrawlTime(new Date());
			CrawlItemIdCache.put(this.crawlItemId);
			if (StringUtils.isNotEmpty(crawlResultDetail.getArticleDetail())) {
				results.add(crawlResultDetail);
			}
		}

		CrawlResultDetailService crawlResultDetailService = SpringUtil.getBean(CrawlResultDetailService.class);
		crawlResultDetailService.addCrawlResultDetails(results);

	}

}
