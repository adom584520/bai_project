package com.pbtd.playlive.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value = { "classpath:constant.properties" })
public class ConstantBeanConfig {
	@Value("${proj_id}")
	public  String proj_id;	//当前项目id
	@Value("${picture_ip}")
	public  String picture_ip;	//当前项目图片服务器
	@Value("${http_ip}")
	public  String http_ip;	//当前项目直播调度服务器域名
	//分平台路径
	@Value("${live_central_http}")
	public String live_central_http;
	
}
