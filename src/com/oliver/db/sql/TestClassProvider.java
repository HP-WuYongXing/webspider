package com.oliver.db.sql;

import java.util.List;
import java.util.Map;

import com.oliver.models.TestClass;

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

/*
 * public void addTestClass(TestClass tc);
	   public List<TestClass> queryAll();
	   public void deleteAll();
	   public TestClass getUser(int id);
 * */

public class TestClassProvider {
	private static String TABLE_NAME="testclass";
	public String getByIdSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
	    WHERE("id = #{id}");
		return SQL();
	}
	
	public String queryAllSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		return SQL();
	}
	
	public String deleteAll(){
		BEGIN();
		DELETE_FROM(TABLE_NAME);
		return SQL();
	}
	
	public String addTestSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("userage","age");
		VALUES("username","name");
		VALUES("useraddress","address");
		return SQL();
	}
}
