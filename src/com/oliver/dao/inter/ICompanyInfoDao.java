package com.oliver.dao.inter;

import com.oliver.models.CompanyInfo;

public interface ICompanyInfoDao {
	public void insertCompanyInfo(CompanyInfo info);
	public CompanyInfo selectByStockCode(String stockCode);
}
