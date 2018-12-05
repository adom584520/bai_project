package com.pbtd.vodinterface.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Configuration
@PropertySource(value = { "classpath:config/redis.properties" })
public class RedisInterfaceBeanConfig {

	//中心平台路径路径
	@Value("${redis_second}")
	public String redis_second;
	
}
