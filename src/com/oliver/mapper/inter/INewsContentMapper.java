package com.oliver.mapper.inter;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;

import com.oliver.db.sql.NewsContentProvider;
import com.oliver.models.NewsContent;

public interface INewsContentMapper {

	@InsertProvider(type=NewsContentProvider.class,method="insertNewsContentSql")
	@SelectKey(keyProperty="id",keyColumn="id", before = false, resultType = int.class, statement = { "SELECT LAST_INSERT_ID() AS ID" })
	public void insertNewsContent(NewsContent newsContent);
	
	@SelectProvider(type=NewsContentProvider.class,method="selectByTitleIdSql")
	@Results(value={
			@Result(id=true,property="id",column="id"),
			@Result(property="subTitle",column="sub_title"),
			@Result(property="source",column="source"),
			@Result(property="titleId",column="title_id"),
			@Result(property="time",column="time"),
			@Result(property="maxLength",column="max_length")
	})
	public NewsContent selectByTitileId(int titleId);
}
