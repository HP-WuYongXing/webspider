package com.oliver.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oliver.dao.impl.StockMarketDao;
import com.oliver.models.StockMarket;
import com.oliver.service.IStockMarketService;

@Service("stockMarketService")
public class StockMarketService implements IStockMarketService {

	@Resource(name="stockMarketDao")
	private StockMarketDao marketDao;
	
	@Override
	public void addStockMarket(StockMarket stockMarket) {
		marketDao.insertStockMarket(stockMarket);
	}
 
}
