package com.oliver.spiders.task;

import com.oliver.spiders.NewsSpider;

public class NewsStockTask implements Runnable{

	private NewsSpider spider;
	public NewsStockTask(NewsSpider spider){
		this.spider = spider;
	}
	
	@Override
	public void run() {
		spider.executedRefreshStocks();
	}

	
}
