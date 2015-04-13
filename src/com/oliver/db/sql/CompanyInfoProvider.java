package com.oliver.db.sql;
import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;  
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;  
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;  
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;  
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE; 
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;  
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
public class CompanyInfoProvider {

	private static final String TABLE_NAME="company_info";
	
	public String insertCompanyInfoSql(){
		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("abbreviation","#{abbreviation}");
		VALUES("stock_code","#{stockCode}");
		VALUES("full_name","#{fullName}");
		VALUES("start_date","#{startDate}");
		VALUES("business","#{business}");
		VALUES("conception","#{conception}");
		VALUES("company_addr","#{companyAddr}");
	    VALUES("representative","#{representative}");
	    VALUES("enquiry_agency","#{enquiryAgency}");
	    VALUES("reg_assets","#{regAssets}");
	    VALUES("reg_address","#{regAddress}");
	    VALUES("office_address","#{officeAddress}");
	    VALUES("company_fax","#{companyFax}");
	    VALUES("email","#{email}");
	    VALUES("website","#{website}");
	    VALUES("contacts","#{contacts}");
	    VALUES("zip_code","#{zipCode}");
	    VALUES("business_scope","#{businessScope}");
	    VALUES("company_info","#{companyInfo}");
	    return SQL();
	}
	
	public String selectByStockIdSql(){
		BEGIN();
		SELECT("*");
		FROM(TABLE_NAME);
		WHERE("stock_id=#{0}");
		return SQL();
	}
}
