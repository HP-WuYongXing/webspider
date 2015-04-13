package com.oliver.service;

import java.util.List;

import com.oliver.models.Picture;

public interface IPictureService {
	public void addPicture(Picture picture);
	public List<Picture> getListByContentId(int contentId);
	public void addPictureList(List<Picture>picList);
}
