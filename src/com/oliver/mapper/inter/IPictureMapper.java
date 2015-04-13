package com.oliver.mapper.inter;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;

import com.oliver.db.sql.PictureProvider;
import com.oliver.models.Picture;

public interface IPictureMapper {
	
	@InsertProvider(type=PictureProvider.class,method="insertPictureSql")
	@SelectKey(keyProperty="id",keyColumn="id", before = false, resultType = int.class, statement = { "SELECT LAST_INSERT_ID() AS ID" })
	public void insertPicture(Picture picture);
	
	@SelectProvider(type=PictureProvider.class,method="selectListByContentIdSql")
	@Results(value={
			@Result(id=true,property="id",column="id"),
			@Result(property="contentId",column="content_id"),
			@Result(property="imagePath",column="image_path"),
			@Result(property="orderNumber",column="order_number")
	})
	public List<Picture> selectListByContentId(int contentId);
}
