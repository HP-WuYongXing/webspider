package com.oliver.service;

import java.util.List;

import com.oliver.models.NewsItem;

public interface INewsItemService {

	public void addNewsItem(NewsItem newsItem);
	public void addNewsItemList(List<NewsItem> list);
	public boolean deleteNewsItem(int id);
	public List<NewsItem> getAll();
    public boolean updateNewsItem(NewsItem newsItem);
    public void deleteAll();
    public void deleteWithUrlAndType(String url,int type);
    public List<NewsItem> getListByType(int type);
    public List<NewsItem> getListByTypeAtLimit(int limit,int type);
    public List<NewsItem> getLIstByTypeAtOffset(int offset,int limit,int type);
    public NewsItem getById(int id);
}
