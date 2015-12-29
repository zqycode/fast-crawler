package org.fast.crawler.demo.crawler.sina;

import org.fast.crawler.demo.entity.CrawlResult;
import org.fast.crawler.core.fetcher.response.ResponseWrapper;
import org.fast.crawler.core.parser.AbstractParser;
import org.fast.crawler.core.utils.JsonUtil;
import org.fast.crawler.core.utils.SpringUtil;
import org.fast.crawler.demo.services.WeiboService;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class SinaParser extends AbstractParser {

	private static final long serialVersionUID = 1L;

	public void parse(ResponseWrapper<?> response) {

		Document document = response.toDocument();
		Elements elements = document.getElementsByTag("script");

		String feedback = "";
		Iterator<Element> iterator = elements.listIterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String html = element.html();
			if (html.contains("pl_weibo_direct\"")) {
				feedback = html.replace("STK && STK.pageletM && STK.pageletM.view(", "");
				feedback = feedback.substring(0, feedback.length() - 1);
				break;
			}
		}
		if (StringUtils.isEmpty(feedback) || !feedback.startsWith("{")) {
			return;
		}
		Map<String, Object> result = JsonUtil.toMap(feedback);
		String welist = (String) result.get("html");
		Document weDoc = Jsoup.parse(welist);
		Elements feedListItems = weDoc.getElementsByAttributeValue("action-type", "feed_list_item");

		List<CrawlResult> results = new ArrayList<CrawlResult>();
		Iterator<Element> items = feedListItems.listIterator();
		while (items.hasNext()) {
			CrawlResult crawlResult = new CrawlResult();
			Element element = items.next();
			if (element.select(".comment .comment_info").size() > 0) { // 忽略转发
				continue;
			}
			String id = element.attr("mid");
			Elements faces = element.select(".face a");
			Element face = faces.first();
			String memberPage = face.attr("href");
			String memberName = face.attr("title");
			String memberImg = face.child(0).attr("src");

			Elements contents = element.getElementsByAttributeValue("node-type", "feed_list_content");
			Element content = contents.first();
			String weiboContent = content.html();

			Elements feedFroms = element.select(".feed_from");
			Element feedFrom = feedFroms.first();
			String weiboFeedFrom = feedFrom.text();

			Elements feedActions = element.select(".feed_action span");
			String forwardNumber;
			String reviewNumber;
			String praiseNumber;
			Iterator<Element> actions = feedActions.listIterator();
			actions.next();
			forwardNumber = actions.next().select("em").html();
			reviewNumber = actions.next().select("em").html();
			praiseNumber = actions.next().select("em").html();

			Elements imgEles = element.getElementsByAttributeValue("node-type", "feed_list_media_prev").select("img");
			if (imgEles.size() > 0) {
				crawlResult.setHasImage(true);
				Iterator<Element> imgIt = imgEles.iterator();
				while (imgIt.hasNext()) {
					Element img = imgIt.next();
					crawlResult.addImage(img.attr("src"));
				}
			}
			crawlResult.setSummary(weiboContent);
			crawlResult.setCommentCount(toInteger(reviewNumber));
			crawlResult.setForwordNum(toInteger(forwardNumber));
			crawlResult.setLikeNum(toInteger(praiseNumber));
			crawlResult.setCrawlItemId(toLong(id));
			crawlResult.setAuthor(memberName);
			crawlResult.setMedia("weibo");
			results.add(crawlResult);
		}
		WeiboService weiboService = SpringUtil.getBean(WeiboService.class);
		weiboService.addCrawlResults(results);
	}

}
