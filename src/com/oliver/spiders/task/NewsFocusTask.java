package com.oliver.spiders.task;

import com.oliver.spiders.NewsSpider;

public class NewsFocusTask implements Runnable{
	
	private NewsSpider spider;
	public NewsFocusTask(NewsSpider spider){
		this.spider =spider;
	}
	
	@Override
	public void run() {
		spider.executedRefreshFocus();
	}
}
