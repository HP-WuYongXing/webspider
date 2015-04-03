package com.oliver.dao.inter;

import java.util.List;
import com.oliver.models.Picture;

public interface IPictureDao {

	public void addPicture(Picture picture);
	public void addPictureList(List<Picture> picList);
	public boolean DeletePicture(int id);
	public List<Picture> queryListByContentId(int contentId);
    public boolean updatePicture(Picture picture);
}
