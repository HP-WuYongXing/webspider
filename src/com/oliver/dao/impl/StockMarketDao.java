package com.oliver.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.oliver.models.StockMarket;
import com.oliver.dao.inter.IStockMarketDao;
import com.oliver.db.DBHelper;
import com.oliver.mapper.inter.IStockMarketMapper;

@Repository("stockMarketDao")
public class StockMarketDao implements IStockMarketDao {

	@Resource(name="stockMarketMapper")
	private IStockMarketMapper marketMapper;
	
	@Override
	public void insertStockMarket(StockMarket stockMarket) {
		marketMapper.insertStockMarket(stockMarket);
	}

	
	
}
