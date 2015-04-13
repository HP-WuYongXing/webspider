package com.oliver.models;

public class NewsItem
{
	private int id;
	private String title;
	private String link;
	private String time;
	private String content;
	private int newsType;
	private int hot;
	private String thumbnail;
	private int stockId;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getNewsType() {
		return newsType;
	}

	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}

	
	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}
	
	

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	@Override
	public String toString() {
		return "NewsItem [id=" + id + ", title=" + title + ", link=" + link
				+ ", time=" + time + ", content=" + content + ", newsType="
				+ newsType + ", hot=" + hot + ", thumbnail=" + thumbnail
				+ ", stockId=" + stockId + "]";
	}

	
	

}
