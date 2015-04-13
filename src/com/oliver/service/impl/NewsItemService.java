package com.oliver.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oliver.dao.impl.NewsItemDao;
import com.oliver.models.NewsItem;
import com.oliver.service.INewsItemService;

@Service("newsItemService")
public class NewsItemService implements INewsItemService {

	@Resource(name="newsItemDao")
	private NewsItemDao itemDao;
	
	public void setItemDao(NewsItemDao itemDao) {
		this.itemDao = itemDao;
	}

	@Override
	public void addNewsItem(NewsItem newsItem) {
		itemDao.insertNewsItem(newsItem);
	}

	@Override
	public void addNewsItemList(List<NewsItem> list) {
		for(NewsItem i:list){
			itemDao.insertNewsItem(i);
		}
		
	}

	@Override
	public boolean deleteNewsItem(int id) {
		itemDao.deleteNewsItem(id);
		return true;
	}

	@Override
	public List<NewsItem> getAll() {
		return itemDao.selectAll();
	}

	@Override
	public boolean updateNewsItem(NewsItem newsItem) {
		itemDao.updateNewsItem(newsItem);
		return true;
	}

	@Override
	public void deleteAll() {
		itemDao.deleteAll();
	}


	@Override
	public void deleteWithUrlAndType(String url, int type) {
		itemDao.deleteWithUrlAndType("%"+url+"%", type);
	}

	@Override
	public List<NewsItem> getListByType(int type) {
		return itemDao.selectListByType(type);
	}
	
	
	@Override
	public List<NewsItem> getListByTypeAtLimit(int limit, int type) {
		return itemDao.selectListByTypeAtLimit(limit,type);
	}

	@Override
	public NewsItem getById(int id) {
		return itemDao.selectById(id);
	}

	@Override
	public List<NewsItem> getLIstByTypeAtOffset(int offset, int limit, int type) {
		return itemDao.selectListByTypeAtOffset(offset, limit, type);
	}
	
	

}
