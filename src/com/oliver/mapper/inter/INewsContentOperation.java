package com.oliver.mapper.inter;

import java.util.List;

import com.oliver.models.NewsContent;

public interface INewsContentOperation {

	public void addNewsContent(NewsContent NewsContent);
	public List<NewsContent> queryListByTitileId(int titleId);
}
