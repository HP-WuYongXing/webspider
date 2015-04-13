package com.oliver.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.oliver.dao.impl.NewsContentDao;
import com.oliver.models.NewsContent;
import com.oliver.service.INewsContentService;

@Service("newsContentService")
public class NewsContentService implements INewsContentService {

	@Resource(name="newsContentDao")
	private NewsContentDao newsContentDao;
	
	@Override
	public void addNewsContent(NewsContent newsContent) {
		newsContentDao.insertNewsContent(newsContent);
	}

	@Override
	public NewsContent getByTitileId(int titleId){
	    return newsContentDao.selectByTitileId(titleId);
	}

}
