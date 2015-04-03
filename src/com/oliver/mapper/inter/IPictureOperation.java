package com.oliver.mapper.inter;

import java.util.List;

import com.oliver.models.Picture;

public interface IPictureOperation {
	public void addPicture(Picture picture);
	public List<Picture> queryListByContentId(int contentId);
}
