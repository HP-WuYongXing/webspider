package com.oliver.spiders.task;

import java.util.List;

import com.oliver.context.BeanLocator;
import com.oliver.models.Stock;
import com.oliver.service.impl.StockService;
import com.oliver.spiders.StockMarketSpider;

public class StockMarketTask implements Runnable{

	private StockMarketSpider spider;
	public StockMarketTask(){
		spider = new StockMarketSpider();
	}
	
	@Override
	public void run() {
		StockService service = (StockService) BeanLocator.getBean("stockService");
		List<Stock> stockList = service.getAll();
		for(Stock s:stockList){
			spider.refreshStockMarket(s);
		}
	}

}
