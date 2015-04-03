package com.oliver.models;

import java.util.List;

public class NewsContent {
	
	private int id;
	private String subTitle;
	private String source;
	private int titleId;
	private String time;
	private List<Paragraph> parList;
	private List<Picture>picList;
	private int maxLength;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	public int getTitleId() {
		return titleId;
	}
	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public List<Paragraph> getParList() {
		return parList;
	}
	public void setParList(List<Paragraph> parList) {
		this.parList = parList;
	}
	public List<Picture> getPicList() {
		return picList;
	}
	public void setPicList(List<Picture> picList) {
		this.picList = picList;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
	@Override
	public String toString() {
		return "NewsContent [id=" + id + ", subTitle=" + subTitle + ", source="
				+ source + ", titleId=" + titleId + ", time=" + time
				+ ", parList=" + parList + ", picList=" + picList
				+ ", maxLength=" + maxLength + "]";
	}
	
	
	
}
