package com.oliver.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oliver.dao.inter.IPictureDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.IPictureOperation;
import com.oliver.models.Picture;

public class PictureDaoImpl implements IPictureDao{

	@Override
	public void addPicture(Picture picture) {
		SqlSessionFactory sessionFactory = DBHelper.getSession();
		SqlSession session = sessionFactory.openSession();
		try {
			IPictureOperation picOp = session
					.getMapper(IPictureOperation.class);
			picOp.addPicture(picture);
			session.commit();
		} finally {
			session.close();
		}
	}
	
	@Override
	public void addPictureList(List<Picture> picList) {
		SqlSessionFactory sessionFactory = DBHelper.getSession();
		SqlSession session = sessionFactory.openSession();
		try {
			IPictureOperation picOp = session
					.getMapper(IPictureOperation.class);
			for(Picture p:picList){
				picOp.addPicture(p);
			}
			session.commit();
		} finally {
			session.close();
		}
	}
	
	

	@Override
	public List<Picture> queryListByContentId(int contentId) {
		SqlSessionFactory sessionFactory = DBHelper.getSession();
		SqlSession session = sessionFactory.openSession();
		List<Picture> picList =null;
		try {
			IPictureOperation picOp = session
					.getMapper(IPictureOperation.class);
		picList = picOp.queryListByContentId(contentId);
			session.commit();
		} finally {
			session.close();
		}
		return picList;
	}

	@Override
	public boolean DeletePicture(int id) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean updatePicture(Picture picture) {
		// TODO Auto-generated method stub
		return false;
	}

}
