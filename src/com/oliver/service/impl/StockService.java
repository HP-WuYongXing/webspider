package com.oliver.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oliver.dao.impl.StockDao;
import com.oliver.models.Stock;
import com.oliver.service.IStockService;

@Service("stockService")
public class StockService implements IStockService {

	@Resource(name="stockDao")
	private StockDao stockDao;
	
	@Override
	public void addStock(Stock stock) {
		stockDao.insertStock(stock);
	}

	@Override
	public List<Stock> getAll() {
		return stockDao.selectAll();
	}

	@Override
	public void deleteAll() {
		stockDao.deleteAll();
	}
}
