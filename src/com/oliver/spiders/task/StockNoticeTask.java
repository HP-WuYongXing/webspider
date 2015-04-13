package com.oliver.spiders.task;

import java.util.List;

import com.oliver.context.BeanLocator;
import com.oliver.models.Stock;
import com.oliver.service.impl.StockService;
import com.oliver.spiders.StockNoticeSpider;

public class StockNoticeTask implements Runnable {

	private StockNoticeSpider noticeSpider;
	public StockNoticeTask(){
		noticeSpider = new StockNoticeSpider();
	}
	
	@Override
	public void run() {
		StockService service = (StockService) BeanLocator.getBean("stockService");
		List<Stock> stockList = service.getAll();
		for(Stock s:stockList){
			noticeSpider.refreshStockNotice(s);
		}
	}

}
