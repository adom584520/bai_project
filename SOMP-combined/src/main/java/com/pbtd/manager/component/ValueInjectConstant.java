package com.pbtd.manager.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.pbtd.manager.util.LoginInfoConstant;

/**
 * 需要使用配置文件注入的值都在这里注入
 * 
 * @author JOJO
 *
 */
@Component
public class ValueInjectConstant {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValueInjectConstant.class);
	@Autowired
	private Environment environment;

	public void setSessionActiveTime() {
		environment.getProperty("mySessionActiveTime");
	}

	public void init() {
		LoginInfoConstant.ADMIN_LOGININFO_PASSWORD = environment.getProperty("system_admin_password");
		LOGGER.info("初始化管理员账号的密码：" + LoginInfoConstant.ADMIN_LOGININFO_PASSWORD);
		setSessionActiveTime();
	}
}
