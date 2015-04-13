package com.oliver.dao.inter;

import java.util.List;

import com.oliver.models.NewsContent;

public interface INewsContentDao {
	public void insertNewsContent(NewsContent newsContent);
    public NewsContent selectByTitileId(int titleId);
}
