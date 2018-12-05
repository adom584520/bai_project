package com.yh.search.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("all")
public class ActiveMQTest {

	@Test
	public void testQueueConsumer() throws Exception {
		//初始化spring容器
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/WEB-INF/app-config/spring-config/applicationContext-activemq.xml");
		System.in.read();
	}
}
