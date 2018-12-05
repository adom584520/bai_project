package com.pbtd.manager.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Configuration
@PropertySource(value = { "classpath:config/liveinterface.properties" })
public class liveInterfaceBeanConfig {

	//中心平台路径路径
	@Value("${live_central_http}")
	public String live_central_http;
	
	//本地项目id`
	@Value("${live_proj_id}")
	public String live_proj_id;
	
	//本地项目路径`
	@Value("${live_interface_http}")
	public String live_interface_http;
}
