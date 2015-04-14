package com.oliver.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.oliver.dao.inter.IPictureDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.IPictureMapper;
import com.oliver.models.Picture;

@Repository("pictureDao")
public class PictureDao implements IPictureDao{

	@Resource(name="pictureMapper")
	private IPictureMapper pictureMapper;
	
	@Override
	public void insertPicture(Picture picture) {
		pictureMapper.insertPicture(picture);
	}

	@Override
	public List<Picture> selectListByContentId(int contentId) {
		return pictureMapper.selectListByContentId(contentId);
	}

	
}
