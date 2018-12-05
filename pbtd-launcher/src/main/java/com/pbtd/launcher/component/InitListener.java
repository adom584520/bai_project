package com.pbtd.launcher.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = LoggerFactory.getLogger(InitListener.class);

	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("服务器启动......");
	}
}