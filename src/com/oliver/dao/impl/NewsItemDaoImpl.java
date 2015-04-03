package com.oliver.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oliver.constants.ConstantsForCommon;
import com.oliver.dao.inter.INewsItemDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.INewsItemOperation;
import com.oliver.models.NewsItem;

public class NewsItemDaoImpl implements INewsItemDao{

	@Override
	public void addNewsItem(NewsItem newsItem) {
		SqlSessionFactory sqlSessionFactory = DBHelper.getSession();
		SqlSession session = sqlSessionFactory.openSession();
		try{
			INewsItemOperation newsItemOp = session.getMapper(INewsItemOperation.class);
			newsItemOp.addNewsItem(newsItem);
			session.commit();
		}finally{
			session.close();
		}
	}

	@Override
	public void addNewsItemList(List<NewsItem> list) {
		SqlSessionFactory sqlSessionFactory = DBHelper.getSession();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			INewsItemOperation newsItemOp = session
					.getMapper(INewsItemOperation.class);
			for(NewsItem item:list){
				newsItemOp.addNewsItem(item);
			}
			session.commit();
		} finally {
			session.close();
		}
	}

	@Override
	public boolean DeleteNewsItem(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<NewsItem> queryAll() {
		SqlSessionFactory sqlSessionFactory = DBHelper.getSession();
		SqlSession session = sqlSessionFactory.openSession();
		List<NewsItem> list;
		try{
			INewsItemOperation newsItemOp = session.getMapper(INewsItemOperation.class);
			list = newsItemOp.selectAll();
			session.commit();
		}finally{
			session.close();
		}
		return list;
	}
	
	

	@Override
	public List<NewsItem> queryNewsItemsAtLimit(int start){
		SqlSessionFactory sqlSessionFactory = DBHelper.getSession();
		SqlSession session = sqlSessionFactory.openSession();
		List<NewsItem> list;
		try{
			INewsItemOperation newsItemOp = session.getMapper(INewsItemOperation.class);
			list = newsItemOp.queryNewsItemsAtLimit(start);
			session.commit();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public boolean updateNewsItem(NewsItem newsItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAll() {
		SqlSessionFactory sqlSessionFactory = DBHelper.getSession();
		SqlSession session = sqlSessionFactory.openSession();
		try{
			INewsItemOperation newsItemOp = session.getMapper(INewsItemOperation.class);
			newsItemOp.deleteAllNewsItems();
			session.commit();
		}finally{
			session.close();
		}
	}
	
	

}
