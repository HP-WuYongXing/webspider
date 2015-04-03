package com.oliver.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oliver.dao.inter.IParagraphDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.IParagraphOperation;
import com.oliver.models.Paragraph;

public class ParagraphDaoImpl implements IParagraphDao{

	@Override
	public void addParagraph(Paragraph paragraph) {
		SqlSessionFactory sqlSessionFactory = DBHelper.getSession();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IParagraphOperation parOp = session
					.getMapper(IParagraphOperation.class);
			parOp.addParagraph(paragraph);
			session.commit();
		} finally {
			session.close();
		}
	}

	@Override
	public void addParagraphList(List<Paragraph> parList) {
		SqlSessionFactory sqlSessionFactory = DBHelper.getSession();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IParagraphOperation parOp = session
					.getMapper(IParagraphOperation.class);
			for(Paragraph p:parList){
				parOp.addParagraph(p);
			}
			session.commit();
		} finally {
			session.close();
		}
	}

	@Override
	public boolean DeleteParagraph(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Paragraph> queryListByContentId(int contentId) {
		SqlSessionFactory sqlSessionFactory = DBHelper.getSession();
		SqlSession session = sqlSessionFactory.openSession();
		List<Paragraph> list=null;
		try {
			IParagraphOperation parOp = session
					.getMapper(IParagraphOperation.class);
			list = parOp.queryListByContentId(contentId);
			session.commit();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public boolean updateParagraph(Paragraph paragraph) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
