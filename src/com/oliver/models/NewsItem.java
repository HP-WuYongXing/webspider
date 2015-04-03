package com.oliver.models;

public class NewsItem
{
	private int id;
	private String title;
	private String link;
	private String time;
	private String content;
	private int newsType;

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

	@Override
	public String toString() {
		return "NewsItem [id=" + id + ", title=" + title + ", link=" + link
				+ ", time=" + time + ", content=" + content + ", newsType="
				+ newsType + "]";
	}

	
	

}
