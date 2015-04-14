package com.oliver.context;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContext {
	
	private static AbstractApplicationContext ctx = 
			new ClassPathXmlApplicationContext("ApplicationContext.xml");
	
	public static AbstractApplicationContext getContext(){
		return ctx;
	}
}
