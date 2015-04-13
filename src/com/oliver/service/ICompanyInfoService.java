package com.oliver.service;

import com.oliver.models.CompanyInfo;

public interface ICompanyInfoService {
	
	public void addCompanyInfo(CompanyInfo info);
	public CompanyInfo getByStockId(int stockId);
}
