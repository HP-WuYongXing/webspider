package com.oliver.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oliver.dao.impl.NewsContentDao;
import com.oliver.dao.impl.ParagraphDao;
import com.oliver.dao.impl.PictureDao;
import com.oliver.models.NewsContent;
import com.oliver.models.Paragraph;
import com.oliver.models.Picture;
import com.oliver.service.impl.NewsContentService;
import com.oliver.service.impl.ParagraphService;
import com.oliver.service.impl.PictureService;

public class NewsContentTest {
	
	public void testNewsContentDao(){
		AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("ApplicationContext.xml");
		NewsContentService dao = (NewsContentService)ctx.getBean("newsContentService");
    NewsContent item= dao.getByTitileId(3579);
    System.out.println("item :"+item);
	}
	
	public void testParagraphDao(){
		AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("ApplicationContext.xml");
		ParagraphDao dao = (ParagraphDao)ctx.getBean("paragraphDao");
		List<Paragraph> list =dao.selectListByContentId(737);
		System.out.println(list);
	}
	

	public void testParagraphService(){
		AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("ApplicationContext.xml");
		ParagraphService service = (ParagraphService)ctx.getBean("paragraphService");
		List<Paragraph> list =service.getListByContentId(737);//selectListByContentId(737);
		System.out.println(list);
	}
	
	public void testPictureDao(){
		AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("ApplicationContext.xml");
		PictureDao dao = (PictureDao)ctx.getBean("pictureDao");
		List<Picture> list =dao.selectListByContentId(748);//selectListByContentId(737);
		System.out.println(list);
	}
	
	@Test
	public void testPictureService(){
		AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("ApplicationContext.xml");
		PictureService service = (PictureService)ctx.getBean("pictureService");
		List<Picture> list =service.getListByContentId(748);//selectListByContentId(737);
		System.out.println(list);
	}
}
