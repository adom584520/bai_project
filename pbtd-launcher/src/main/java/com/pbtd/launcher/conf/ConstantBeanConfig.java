package com.pbtd.launcher.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:config/constant.properties" })
public class ConstantBeanConfig {
	@Value("${proj_id}")
	public String projId;	//项目ID
}
