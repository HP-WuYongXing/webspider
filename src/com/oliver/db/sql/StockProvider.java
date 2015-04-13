package com.oliver.db.sql;
import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;  
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;  
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;  
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;  
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE; 
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;  
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
public class StockProvider {

	private static final String TABLE_NAME="stock";
	
	public String insertStockSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("code","#{code}");
		VALUES("name","#{name}");
		return SQL();
	}
	
	public String selectAllSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		return SQL();
	}
	
	public String deleteAllSql(){
		BEGIN();
		DELETE_FROM(TABLE_NAME);
		return SQL();
	}
}
