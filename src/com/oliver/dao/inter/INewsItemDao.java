package com.oliver.dao.inter;

import java.util.List;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;

public interface INewsItemDao {
	public void addNewsItem(NewsItem newsItem);
	public void addNewsItemList(List<NewsItem> list);
	public boolean DeleteNewsItem(int id);
	public List<NewsItem> queryAll();
    public boolean updateNewsItem(NewsItem newsItem);
    public void deleteAll();
    public List<NewsItem>queryNewsItemsAtLimit(int start);
}
