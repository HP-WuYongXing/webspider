package com.oliver.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.oliver.dao.inter.INewsContentDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.INewsContentMapper;
import com.oliver.models.NewsContent;

@Repository("newsContentDao")
public class NewsContentDao implements INewsContentDao{

	@Resource(name="newsContentMapper")
	private INewsContentMapper newsContentMapper;
	
	@Override
	public void insertNewsContent(NewsContent newsContent) {
		newsContentMapper.insertNewsContent(newsContent);
	}
	
	

	@Override
	public NewsContent selectByTitileId(int titleId) {
		return newsContentMapper.selectByTitileId(titleId);
	}
}
