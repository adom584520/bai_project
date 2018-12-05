package com.pbtd.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactory {

	private static ApplicationContext appContext=new ClassPathXmlApplicationContext("/applicationContext.xml");
	
	public static Object getBean(Class clazz){
		return appContext.getBean(clazz);
	}
}
