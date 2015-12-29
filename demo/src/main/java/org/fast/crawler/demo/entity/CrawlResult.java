package org.fast.crawler.demo.entity;

import org.fast.crawler.demo.entity.support.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xp017734 on 10/9/15.
 */
@Entity
@Table(name = "crawl_result")
public class CrawlResult extends AbstractEntity {

	private Long crawlItemId;

	@Column(length = 1000)
	private String title;

	@Column(length = 5000)
	private String summary;

	private String media;

	@ElementCollection
	private List<String> imageList = new ArrayList<String>();

	private String dateTime;

	private String keyword;

	private Boolean hasVideo;

	private Boolean hasMp4Video;

	private Integer articleType;

	private Long articleId;

	private String tag;

	private Integer commentCount;

	private Long publicTime;

	private Long createTime;

	private Boolean hasImage = false;

	private Integer likeNum;

	private Integer unlikeNum;

	private Integer forwordNum;

	private String source;

	private String author;

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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public void addImage(String url) {
		this.imageList.add(url);
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Boolean getHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(Boolean hasVideo) {
		this.hasVideo = hasVideo;
	}

	public Boolean getHasMp4Video() {
		return hasMp4Video;
	}

	public void setHasMp4Video(Boolean hasMp4Video) {
		this.hasMp4Video = hasMp4Video;
	}

	public Integer getArticleType() {
		return articleType;
	}

	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Long getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Long publicTime) {
		this.publicTime = publicTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Boolean getHasImage() {
		return hasImage;
	}

	public void setHasImage(Boolean hasImage) {
		this.hasImage = hasImage;
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	public Integer getUnlikeNum() {
		return unlikeNum;
	}

	public void setUnlikeNum(Integer unlikeNum) {
		this.unlikeNum = unlikeNum;
	}

	public Integer getForwordNum() {
		return forwordNum;
	}

	public void setForwordNum(Integer forwordNum) {
		this.forwordNum = forwordNum;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
