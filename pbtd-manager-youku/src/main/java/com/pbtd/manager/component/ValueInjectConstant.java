package com.pbtd.manager.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 需要使用配置文件注入的值都在这里注入
 * 
 * @author JOJO
 *
 */
@Component
public class ValueInjectConstant {
	@Autowired
	private Environment environment;

	public void setSessionActiveTime() {
		environment.getProperty("mySessionActiveTime");
	}

	public void init() {
		setSessionActiveTime();
	}
}
