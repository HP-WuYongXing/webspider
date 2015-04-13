package com.oliver.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oliver.dao.inter.INewsItemDao;
import com.oliver.mapper.inter.INewsItemMapper;
import com.oliver.models.NewsItem;

@Repository("newsItemDao")
public class NewsItemDao implements INewsItemDao{
	
	@Resource(name="newsItemMapper")
	private INewsItemMapper itemMapper;

	@Override
	public void insertNewsItem(NewsItem newsItem) {
		itemMapper.insertNewsItem(newsItem);
	}

	@Override
	public boolean deleteNewsItem(int id) {
		itemMapper.deleteById(id);
		return true;
	}

	@Override
	public List<NewsItem> selectAll() {
		return itemMapper.selectAll();
	}

	@Override
	public boolean updateNewsItem(NewsItem newsItem) {
		itemMapper.updateNewsItem(newsItem);
		return true;
	}

	@Override
	public void deleteAll() {
		itemMapper.deleteAllNewsItems();
	}

	@Override
	public void deleteWithUrlAndType(String url, int type) {
		itemMapper.deleteWithUrlAndType(url, type);
	}

	@Override
	public List<NewsItem> selectListByTypeAtOffset(int offset, int limit,int type) {
		return itemMapper.selectListByTypeAtOffset(offset, limit,type);
	}

	@Override
	public List<NewsItem> selectListByType(int type) {
		return itemMapper.selectListByType(type);
	}

	@Override
	public List<NewsItem> selectListByTypeAtLimit(int limit, int type) {
		return itemMapper.selectListByTypeAtLimit(limit,type);
	}

	@Override
	public NewsItem selectById(int id) {
		return itemMapper.selectById(id);
	}
	
	
	
	
}
