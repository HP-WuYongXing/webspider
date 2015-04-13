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
public class NewsItemProvider {

	private static final String TABLE_NAME="news_item";
	
	public String insertNewsItemSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("title","#{title}");
		VALUES("time","#{time}");
		VALUES("link","#{link}");
		VALUES("type","#{newsType}");
		VALUES("url_code","#{urlCode}");
		return SQL();
	}
	
	public String updateNewsItemSql(){
		BEGIN();
		UPDATE(TABLE_NAME);
		SET("title=#{title},time=#{time},link=#{link},type=#{newsType},"
				+ "hot=#{hot},thumbnail=#{thumbnail},url_code=#{urlCode}");
		WHERE("id=#{id}");
		return SQL();
	}
	
	public String selectByIdSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		WHERE("id=#{id}");
		return SQL();
	}
	
	public String selectAllSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		return SQL();
	}
	
	public String selectListByTypeAtOffsetSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		WHERE("type=#{2}");
		return SQL()+"limit #{0},#{1}";
	}
	
	public String selectListByTypeAtLimitSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		WHERE("type = #{1}");
		return SQL()+"limit #{0}";
	}
	
	public String deleteAllNewsItemsSql(){
		BEGIN();
		DELETE_FROM(TABLE_NAME);
		return SQL();
	}
	
	public String deleteByTypeAndUrlCodeSql(){
		BEGIN();
		DELETE_FROM(TABLE_NAME);
		WHERE("type=#{0} and url_code = #{1}");
		return SQL();
	}
	
	public String deleteByIdSql(){
		BEGIN();
		DELETE_FROM(TABLE_NAME);
		WHERE("id=#{id}");
		return SQL();
	}
	
	public String selectListByTypeSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		WHERE("type=#{0}");
		return SQL();
	}
	
	public String selectByTypeAndUrlCode(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		WHERE("type=#{0} and url_code=#{1}");
		return SQL();
	}
}
