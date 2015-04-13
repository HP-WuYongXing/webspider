package com.oliver.service;

import java.util.List;

import com.oliver.models.NewsContent;

public interface INewsContentService {
	public void addNewsContent(NewsContent newsContent);
    public NewsContent getByTitileId(int titleId);
}
