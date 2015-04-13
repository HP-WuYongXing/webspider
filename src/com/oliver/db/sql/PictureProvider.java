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

public class PictureProvider {

	private static final String TABLE_NAME="picture";
	
	public String insertPictureSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("image_path","#{imagePath}");
		VALUES("content_id","#{contentId}");
		VALUES("order_number","#{orderNumber}");
		return SQL();
	}
	
	public String selectListByContentIdSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		WHERE("content_id= #{contentId}");
		return SQL();
	}
}
