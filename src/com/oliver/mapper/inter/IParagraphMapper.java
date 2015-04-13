package com.oliver.mapper.inter;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;

import com.oliver.db.sql.ParagraphProvider;
import com.oliver.models.Paragraph;

public interface IParagraphMapper {
	
	@InsertProvider(type=ParagraphProvider.class,method="insertParagraphSql")
	@SelectKey(keyProperty="id",keyColumn="id", before = false, resultType = int.class, statement = { "SELECT LAST_INSERT_ID() AS ID" })
	public void insertParagraph(Paragraph paragraph);
	
	@SelectProvider(type=ParagraphProvider.class,method="getListByContentIdSql")
	@Results(value={
			@Result(id=true,property="id",column="id"),
			@Result(property="orderNumber",column="order_number"),
			@Result(property="contentId",column="content_id"),
			@Result(property="content",column="content")
	})
	public List<Paragraph> getListByContentId(int contentId);
}
