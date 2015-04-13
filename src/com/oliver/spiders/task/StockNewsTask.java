package com.oliver.spiders.task;

import java.util.List;

import com.oliver.context.BeanLocator;
import com.oliver.models.Stock;
import com.oliver.service.impl.StockService;
import com.oliver.spiders.StockNewsSpider;

public class StockNewsTask implements Runnable {

	private StockNewsSpider spider;
	
	public StockNewsTask(){
		spider = new StockNewsSpider();
	}
	
	@Override
	public void run() {
		StockService service = (StockService) BeanLocator.getBean("stockService");
		List<Stock> stockList = service.getAll();
		for(Stock s:stockList){
		   spider.refreshStockNews(s);	
		}
	}

}
