package com.oliver.spiders.task;

import com.oliver.spiders.StockSpider;

public class StockListTask implements Runnable {

	private StockSpider spider;
	
	public StockListTask(){
		spider = new StockSpider();
	}
	
	@Override
	public void run() {
		spider.executRefreshStock();
	}

}
