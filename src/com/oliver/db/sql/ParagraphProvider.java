package com.oliver.db.sql;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;  
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;  
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;  
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;  
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;  
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;  
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;  
import static org.apache.ibatis.jdbc.SqlBuilder.SET;  
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;  
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;

public class ParagraphProvider {

	private static final String TABLE_NAME="paragraph";
	
	public String insertParagraphSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("order_number","#{orderNumber}");
	    VALUES("content_id","#{contentId}");
	    VALUES("content","#{content}");
	    return SQL();
	}
	
	public String getListByContentIdSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		WHERE("content_id=#{contentId}");
		return SQL();
	}
}
