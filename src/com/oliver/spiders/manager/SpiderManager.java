package com.oliver.spiders.manager;

import com.oliver.spiders.FinanceCESpider;
import com.oliver.spiders.FinanceCSSpider;
import com.oliver.spiders.FinanceHeXunSpider;
import com.oliver.spiders.FinanceP5wSpider;
import com.oliver.spiders.FinanceQQSpider;
import com.oliver.spiders.FinanceSTCNSpider;
import com.oliver.spiders.FinanceSinaHeaderSpider;
import com.oliver.spiders.FinanceSinaSpider;
import com.oliver.spiders.Money163Spider;
import com.oliver.spiders.StockCompanyCommonInfoSpider;
import com.oliver.spiders.StockMarketSpider;
import com.oliver.spiders.StockNewsSpider;
import com.oliver.spiders.StockNoticeSpider;
import com.oliver.spiders.StockReportSpider;
import com.oliver.spiders.StockSpider;

public class SpiderManager {
	//private FinanceCESpider ceSpider;
	private FinanceCSSpider csSpider;
	private FinanceHeXunSpider heXunSpider;
	private FinanceP5wSpider p5wSpider;
	private FinanceQQSpider qqSpider;
	private FinanceSinaHeaderSpider sinaHeaderSpider;
	private FinanceSinaSpider sinaSpider;
	private FinanceSTCNSpider stcnSpider;
	private Money163Spider money163Spider;
	private StockMarketSpider marketSpider;
	private StockNewsSpider stockNewsSpider;
	private StockNoticeSpider stockNoticeSpider;
	private StockReportSpider stockReportSpider;
	private StockSpider stockSpider;
	/*
	 * 刷新策略：
	 * 新闻每1小时刷新一次，stockMarket每2分钟刷新一次，stock 资料  每天刷新一次
	 * 新闻类：一个spider独占5个线程
	 * stock 资料 使用10个线程处理。
	 * stockMarket 使用10个线程刷新。
	 */
	FinanceCESpider spider = new FinanceCESpider();
	public void executeRefreshTask(){
		
		new Thread(new CESpiderTask()).start();
	}
	
	class CESpiderTask implements Runnable{

		@Override
		public void run() {
			System.out.println("run....");
		
			spider.executedRefreshFocus();
		}
		
	}
}
