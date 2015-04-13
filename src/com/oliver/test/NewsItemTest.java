package com.oliver.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oliver.dao.impl.NewsItemDao;
import com.oliver.models.NewsItem;
import com.oliver.service.impl.NewsItemService;

public class NewsItemTest {

	@Test
	public void testNewsItemDao(){
		AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("ApplicationContext.xml");
		NewsItemService newsItemService = (NewsItemService)ctx.getBean("newsItemService");
    List<NewsItem> item= newsItemService.getAll();
    System.out.println("item size:"+item);
	}
}
