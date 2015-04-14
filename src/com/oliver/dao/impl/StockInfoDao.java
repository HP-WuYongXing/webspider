package com.oliver.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.oliver.models.StockInfo;
import com.oliver.dao.inter.IStockInfoDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.IStockInfoMapper;

@Repository("stockInfoDao")
public class StockInfoDao implements IStockInfoDao {

	@Resource(name="stockInfoMapper")
	private IStockInfoMapper mapper;
	
	@Override
	public void insertStockInfo(StockInfo si) {
		mapper.insertStockInfo(si);
	}

	
}
