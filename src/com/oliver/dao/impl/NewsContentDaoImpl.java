package com.oliver.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oliver.dao.inter.INewsContentDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.INewsContentOperation;
import com.oliver.models.NewsContent;

public class NewsContentDaoImpl implements INewsContentDao{

	@Override
	public void addNewsContent(NewsContent newsContent) {
		SqlSessionFactory sqlSessionFactory = DBHelper.getSession();
		SqlSession session = sqlSessionFactory.openSession();
		try{
		INewsContentOperation contentOp = session.getMapper(INewsContentOperation.class);
		contentOp.addNewsContent(newsContent);
		session.commit();
		}finally{
			session.close();
		}
	}
	
	

	@Override
	public List<NewsContent> queryByTitileId(int titleId) {
		SqlSessionFactory sessionFactory = DBHelper.getSession();
		SqlSession session = sessionFactory.openSession();
		List<NewsContent>list=null;
		try{
			INewsContentOperation contentOp =  session.getMapper(INewsContentOperation.class);
		     list =contentOp.queryListByTitileId(titleId);
		    session.commit();
		}finally{
			session.close();
		}
		return list;
	}



	@Override
	public boolean DeleteNewsContent(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<NewsContent> query() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateNewsContent(NewsContent newsContent) {
		// TODO Auto-generated method stub
		return false;
	}

}
