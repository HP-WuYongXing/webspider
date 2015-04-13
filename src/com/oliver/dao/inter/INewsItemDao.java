package com.oliver.dao.inter;

import java.util.List;

import com.oliver.models.NewsContent;
import com.oliver.models.NewsItem;

public interface INewsItemDao {
	public void insertNewsItem(NewsItem newsItem);
	public boolean deleteNewsItem(int id);
	public List<NewsItem> selectAll();
    public boolean updateNewsItem(NewsItem newsItem);
    public void deleteAll();
    public void deleteWithUrlAndType(String url,int type);
    public List<NewsItem> selectListByTypeAtOffset(int offset,int limit, int type);
    public List<NewsItem> selectListByType(int type);
    public List<NewsItem> selectListByTypeAtLimit(int limit,int type);
    public NewsItem selectById(int id);
}
