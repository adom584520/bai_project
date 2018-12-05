package com.pbtd.playlive.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationListener.class);
	//@Autowired
	//private ValueInjectConstant diConstant;
	//@Autowired
//	private LoginInfoService loginInfoService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("初始化静态常量值......");
		// 初始化静态常量值
		//diConstant.init();
		try {
			// 创建超级管理员账号
		//	logger.info("校验数据库是否存在管理员账号......");
			//LoginInfo admin = loginInfoService.queryAdmin();
			//if (admin == null) {
			//	logger.info("创建管理员账号......");
				//loginInfoService.insertAdmin();
			//}
		} catch (Exception e) {
			logger.error("管理员创建失败！", e);
		}
	}
}