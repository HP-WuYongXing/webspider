package com.oliver.spiders.task;

import java.util.List;

import com.oliver.constants.ConstantsForStock;
import com.oliver.context.BeanLocator;
import com.oliver.models.Stock;
import com.oliver.service.impl.StockService;
import com.oliver.spiders.StockReportSpider;

public class StockReportTask implements Runnable {

	private StockReportSpider spider;
	public StockReportTask(){
		spider = new StockReportSpider();
	}
	
	@Override
	public void run() {
		StockService service = (StockService)BeanLocator.getBean("stockService");
		List<Stock> stockList = service.getListByType(ConstantsForStock.STOCK_TYPE_NORMAL);
		for(Stock s:stockList){
			spider.excutedRefreshReport(s);
		}
	}

}
