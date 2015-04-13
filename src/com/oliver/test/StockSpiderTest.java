package com.oliver.test;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oliver.spiders.StockSpider;

public class StockSpiderTest {

//	@Test
//	public void testGetStockList(){
//		StockSpider spider = new StockSpider();
//		List<Stock> list =spider.getStocks();
//		int cnt=0;
//		for(Stock s:list){
//			System.out.println((cnt++)+":"+s);
//		}
//	}
	
	@Test
	public void testRefreshStock(){
		 AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("ApplicationContext.xml");
		StockSpider spider = new StockSpider();
		spider.executRefreshStock();
	}
}
