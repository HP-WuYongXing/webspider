package com.oliver.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oliver.dao.impl.TestClassDao;
import com.oliver.models.TestClass;

public class TestClassTest {

	@Test
	public void testSpring(){
		AbstractApplicationContext ctx = 
				new ClassPathXmlApplicationContext("ApplicationContext.xml");
    TestClassDao dao = (TestClassDao)ctx.getBean("testClassDao");
    List<TestClass> list = dao.queryAll();
//	TestClass t = dao.getUser(1);
		System.out.println(list);
	}
}
