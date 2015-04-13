package com.oliver.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oliver.dao.impl.PictureDao;
import com.oliver.models.Picture;
import com.oliver.service.IPictureService;

@Service("pictureService")
public class PictureService implements IPictureService {

	@Resource(name="pictureDao")
	private PictureDao picDao;
	
	@Override
	public void addPicture(Picture picture) {
		picDao.insertPicture(picture);
	}

	@Override
	public List<Picture> getListByContentId(int contentId) {
		return picDao.selectListByContentId(contentId);
	}

	@Override
	public void addPictureList(List<Picture> picList) {
		for(Picture p:picList){
			addPicture(p);
		}
	}

	
}
