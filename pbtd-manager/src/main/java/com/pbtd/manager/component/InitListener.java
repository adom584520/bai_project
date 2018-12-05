package com.pbtd.manager.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.service.LoginInfoService;

@Component
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(InitListener.class);
	@Autowired
	private ValueInjectConstant diConstant;
	@Autowired
	private LoginInfoService loginInfoService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("初始化静态常量值......");
		// 初始化静态常量值
		diConstant.init();
		try {
			// 创建超级管理员账号
			LOGGER.info("校验数据库是否存在管理员账号......");
			LoginInfo admin = loginInfoService.queryAdmin();
			if (admin == null) {
				LOGGER.info("创建管理员账号......");
				loginInfoService.insertAdmin();
				LOGGER.info("管理员账号创建成功......");
			}
		} catch (Exception e) {
			LOGGER.error("管理员账号创建失败！", e);
		}
		LOGGER.info("初始化数据成功！");
	}
}