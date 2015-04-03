package com.oliver.dao.inter;

import java.util.List;

import com.oliver.models.NewsContent;

public interface INewsContentDao {
	public void addNewsContent(NewsContent newsContent);
	public boolean DeleteNewsContent(int id);
	public List<NewsContent> query();
    public boolean updateNewsContent(NewsContent newsContent);
    public List<NewsContent> queryByTitileId(int titleId);
}
