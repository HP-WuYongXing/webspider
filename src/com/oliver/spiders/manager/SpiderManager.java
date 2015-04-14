package com.oliver.spiders.manager;

import java.util.ArrayList;
import java.util.List;

import com.oliver.spiders.FinanceCESpider;
import com.oliver.spiders.FinanceCSSpider;
import com.oliver.spiders.FinanceHeXunSpider;
import com.oliver.spiders.FinanceP5wSpider;
import com.oliver.spiders.FinanceQQSpider;
import com.oliver.spiders.FinanceSTCNSpider;
import com.oliver.spiders.FinanceSinaHeaderSpider;
import com.oliver.spiders.FinanceSinaSpider;
import com.oliver.spiders.Money163Spider;
import com.oliver.spiders.NewsSpider;
import com.oliver.spiders.StockCompanyCommonInfoSpider;
import com.oliver.spiders.StockMarketSpider;
import com.oliver.spiders.StockNewsSpider;
import com.oliver.spiders.StockNoticeSpider;
import com.oliver.spiders.StockReportSpider;
import com.oliver.spiders.StockSpider;
import com.oliver.spiders.task.NewsFocusTask;
import com.oliver.spiders.task.NewsStockTask;
import com.oliver.spiders.task.StockNewsTask;

public class SpiderManager {
	//private FinanceCESpider ceSpider;
	private FinanceCSSpider csSpider=new FinanceCSSpider();
	private FinanceHeXunSpider heXunSpider = new FinanceHeXunSpider();
	private FinanceP5wSpider p5wSpider = new FinanceP5wSpider();
	private FinanceQQSpider qqSpider = new FinanceQQSpider();
	private FinanceSinaHeaderSpider sinaHeaderSpider = new FinanceSinaHeaderSpider();
	private FinanceSinaSpider sinaSpider = new FinanceSinaSpider();
	private FinanceSTCNSpider stcnSpider = new FinanceSTCNSpider();
	private Money163Spider money163Spider = new Money163Spider();
	private StockMarketSpider marketSpider = new StockMarketSpider();
	private StockNewsSpider stockNewsSpider = new StockNewsSpider();
	private StockNoticeSpider stockNoticeSpider = new StockNoticeSpider();
	private StockReportSpider stockReportSpider = new StockReportSpider();
	private StockSpider stockSpider = new StockSpider();
	private List<NewsSpider> newsSpiderList;
	/*
	 * ˢ�²��ԣ�
	 * ����ÿ1Сʱˢ��һ�Σ�stockMarketÿ2����ˢ��һ�Σ�stock ����  ÿ��ˢ��һ��
	 * �����ࣺһ��spider��ռ5���߳�
	 * stock ���� ʹ��10���̴߳���
	 * stockMarket ʹ��10���߳�ˢ�¡�
	 */
	public void init(){
		newsSpiderList = new ArrayList<NewsSpider>();
		newsSpiderList.add(csSpider);
		newsSpiderList.add(heXunSpider);
		newsSpiderList.add(p5wSpider);
		newsSpiderList.add(qqSpider);
		newsSpiderList.add(sinaHeaderSpider);
		newsSpiderList.add(sinaSpider);
		newsSpiderList.add(stcnSpider);
		newsSpiderList.add(money163Spider);
	}
	
	public void refreshFinanceNews(){
		for(NewsSpider s:newsSpiderList){
			NewsFocusTask focusTask = new NewsFocusTask(s);
			new Thread(focusTask).start();
		}
		for(NewsSpider s:newsSpiderList){
			NewsStockTask stockTask = new NewsStockTask(s);
			new Thread(stockTask).start();
		}
		new Thread(new StockNewsTask()).start();
	}
}
