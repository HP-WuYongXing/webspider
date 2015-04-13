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
public class StockInfoProvider {
	
	private static final String TABLE_NAME="stock_info";
	
	public String insertStockInfoSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("issuing_date","#{issuingDate}");
		VALUES("listing_date","#{listingDate}");
		VALUES("exchange","#{exchange}");
		VALUES("category","#{category}");
		VALUES("circulating_stock","#{circulatingStock}");
		VALUES("total_stock","#{totalStock}");
		VALUES("consignee","#{consignee}");
		VALUES("company_id","#{companyId}");
		return SQL();
	}
}
