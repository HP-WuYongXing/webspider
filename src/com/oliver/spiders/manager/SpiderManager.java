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
	 * ˢ�²��ԣ�
	 * ����ÿ1Сʱˢ��һ�Σ�stockMarketÿ2����ˢ��һ�Σ�stock ����  ÿ��ˢ��һ��
	 * �����ࣺһ��spider��ռ5���߳�
	 * stock ���� ʹ��10���̴߳���
	 * stockMarket ʹ��10���߳�ˢ�¡�
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
