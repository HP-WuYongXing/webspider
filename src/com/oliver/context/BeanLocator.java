package com.oliver.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class BeanLocator implements BeanFactoryAware {
 
	private static BeanFactory beanFactory = null;
	private static BeanLocator beanLocator=null;
	
	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		beanFactory = factory;
	}
	
	public BeanFactory getBeanFactory(){
		return beanFactory;
	}
	
	public static BeanLocator getInstance(){
		if(beanLocator==null){
			beanLocator = (BeanLocator)beanFactory.getBean("beanLocator");
		}
		return beanLocator;
	}
	
	public static Object getBean(String beanName){
		return beanFactory.getBean(beanName);
	}
}
