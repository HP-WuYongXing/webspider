package com.oliver.dao.inter;

import java.util.List;
import com.oliver.models.Picture;

public interface IPictureDao {
	public void insertPicture(Picture picture);
	public List<Picture> selectListByContentId(int contentId);
}
