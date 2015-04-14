package com.oliver.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oliver.context.AppContext;
import com.oliver.dao.impl.StockDao;
import com.oliver.models.NewsItem;
import com.oliver.models.Stock;
import com.oliver.service.impl.NewsItemService;
import com.oliver.service.impl.StockService;
import com.oliver.spiders.StockSpider;

public class StockTest {

	public void testStockDao(){
		AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("ApplicationContext.xml");
		StockDao dao = (StockDao)ctx.getBean("stockDao");
    List<Stock> item= dao.selectAll();
    System.out.println("item size:"+item);
	}
	

	public void testDaoService(){
		AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("ApplicationContext.xml");
		StockService service = (StockService)ctx.getBean("stockService");
    List<Stock> item= service.getAll();
    System.out.println("item size:"+item);
	}
	
	@Test
	public void testStockSpider(){
		AppContext.getContext();
		StockSpider spider = new StockSpider();
		spider.executRefreshStock();
	}
}
