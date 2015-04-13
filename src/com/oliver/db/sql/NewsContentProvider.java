package com.oliver.db.sql;
import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;  
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;  
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;  
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;  
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE; 
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;  
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
public class NewsContentProvider {

	private static final String TABLE_NAME="news_content";
	
	public String insertNewsContentSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("sub_title","#{subTitle}");
		VALUES("source","#{source}");
		VALUES("title_id","#{titleId}");
		VALUES("time","#{time}");
		VALUES("max_length","#{maxLength}");
		return SQL();
	}
	
	public String selectByTitleIdSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		WHERE("title_id=#{titleId}");
		return SQL();
	}
}
