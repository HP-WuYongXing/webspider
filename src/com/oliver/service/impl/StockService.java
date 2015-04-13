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
		addPrefix(stock);
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
	
	private void addPrefix(Stock stock){
		String code= stock.getCode();
		if(code.startsWith("600")||
		   code.startsWith("700")||
		   code.startsWith("710")||
		   code.startsWith("701")||
		   code.startsWith("711")||
		   code.startsWith("720")||
		   code.startsWith("730")||
		   code.startsWith("735")||
		   code.startsWith("737")||
		   code.startsWith("900")){
		   stock.setPrefix("sh");
		}else{
			stock.setPrefix("sz");
		}
	}
}
