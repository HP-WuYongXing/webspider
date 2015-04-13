package com.oliver.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.oliver.models.CompanyInfo;
import com.oliver.dao.inter.ICompanyInfoDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.ICompanyInfoMapper;

@Repository("companyInfoDao")
public class CompanyInfoDao implements ICompanyInfoDao{

	@Resource(name="companyInfoMapper")
    private ICompanyInfoMapper mapper;
	
	@Override
	public void insertCompanyInfo(CompanyInfo info) {
		mapper.insertCompanyInfo(info);
	}

	@Override
	public CompanyInfo selectByStockId(int stockId) {
		return mapper.selectByStockId(stockId);
	}

	

}
