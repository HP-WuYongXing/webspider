package com.oliver.mapper.inter;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Result;
import com.oliver.db.sql.CompanyInfoProvider;
import com.oliver.models.CompanyInfo;

public interface ICompanyInfoMapper {
	@InsertProvider(type = CompanyInfoProvider.class,method="insertCompanyInfoSql")
	@SelectKey(keyProperty="id",keyColumn="id", before = false, resultType = int.class, statement = { "SELECT LAST_INSERT_ID() AS ID" })
    public void insertCompanyInfo(CompanyInfo ci);
	
	@SelectProvider(type = CompanyInfoProvider.class,method="selectByStockIdSql")
	@Results(value={
			@Result(id=true,property="id",column="id"),
			@Result(property="abbreviation",column="abbreviation"),
			@Result(property="stockCode",column="stock_code"),
			@Result(property="fullName",column="full_name"),
			@Result(property="startDate",column="start_date"),
			@Result(property="business",column="business"),
			@Result(property="conception",column="conception"),
			@Result(property="companyAddr",column="company_addr"),
			@Result(property="representative",column="representative"),
			@Result(property="enquiryAgency",column="enquiry_agency"),
			@Result(property="regAssets",column="reg_assets"),
			@Result(property="regAddress",column="reg_address"),
			@Result(property="officeAddress",column="office_address"),
			@Result(property="companyFax",column="company_fax"),
			@Result(property="email",column="email"),
			@Result(property="website",column="website"),
			@Result(property="contacts",column="contacts"),
			@Result(property="zipCode",column="zip_code"),
			@Result(property="businessScope",column="business_scope"),
			@Result(property="companyInfo",column="company_info"),
			@Result(property="stockId",column="stock_id")
	})
	public CompanyInfo selectByStockId(int stockId);
}
