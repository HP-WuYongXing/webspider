package com.oliver.mapper.inter;

import java.util.List;

import com.oliver.models.NewsItem;

public interface INewsItemOperation {

	public void addNewsItem(NewsItem item);
	public NewsItem selectNewsItemByID(int id);
	public void deleteAllNewsItems();
	public List<NewsItem> selectAll();
	public List<NewsItem>queryNewsItemsAtLimit(int start);
}
