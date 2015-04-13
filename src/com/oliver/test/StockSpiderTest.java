package com.oliver.test;

import org.junit.Test;

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
		StockSpider spider = new StockSpider();
		spider.executRefreshStock();
	}
}
