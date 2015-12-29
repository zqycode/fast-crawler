package org.fast.crawler.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.fast.crawler.demo.entity.support.AbstractEntity;

@Entity
@Table(name = "crawl_result_detail")
public class CrawlResultDetail extends AbstractEntity {

	private Long crawlItemId;

	private String title;

	@Column(length = 20000)
	private String articleDetail;

	private String time;

	private String source;

	private Integer commentNum;

	private Date crawlTime;

	public Long getCrawlItemId() {
		return crawlItemId;
	}

	public void setCrawlItemId(Long crawlItemId) {
		this.crawlItemId = crawlItemId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticleDetail() {
		return articleDetail;
	}

	public void setArticleDetail(String articleDetail) {
		this.articleDetail = articleDetail;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Date getCrawlTime() {
		return crawlTime;
	}

	public void setCrawlTime(Date crawlTime) {
		this.crawlTime = crawlTime;
	}

}
