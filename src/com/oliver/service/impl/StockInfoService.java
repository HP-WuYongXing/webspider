package com.oliver.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oliver.dao.impl.StockInfoDao;
import com.oliver.models.StockInfo;
import com.oliver.service.IStockInfoService;

@Service("stockInfoService")
public class StockInfoService implements IStockInfoService {

	@Resource(name="stockInfoDao")
	private StockInfoDao stockInfoDao;
	
	@Override
	public void addStockInfo(StockInfo si) {
		stockInfoDao.insertStockInfo(si);
	}

}
