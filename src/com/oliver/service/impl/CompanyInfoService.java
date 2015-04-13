package com.oliver.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oliver.dao.impl.CompanyInfoDao;
import com.oliver.models.CompanyInfo;
import com.oliver.service.ICompanyInfoService;

@Service("companyInfoService")
public class CompanyInfoService implements ICompanyInfoService {

	@Resource(name="companyInfoDao")
	private CompanyInfoDao ciDao;
	
	@Override
	public void addCompanyInfo(CompanyInfo info) {
		ciDao.insertCompanyInfo(info);
	}

	@Override
	public CompanyInfo getByStockId(int stockId) {
		return ciDao.selectByStockId(stockId);
	}

	
}
