package com.pbtd.manager.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:config/constant.properties" })
public class ConstantBeanConfig {
	@Value("${total}")
	public int total;// 查询专辑
	@Value("${cp_code}")
	public String cpCode;// 己方cpCode

}
