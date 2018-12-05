package com.pbtd.vodinterface.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:config/redis.properties" })
public class RedisInterfaceBeanConfig {

	// Redis KEY过期时间（秒）
	@Value("${redis_second}")
	public String redis_second;
	// Redis KEY命名
	@Value("${redis_key}")
	public String redis_key;
	// Redis状态0:未开启 1:开启
	@Value("${redis_status}")
	public String redis_status;
	// solrClient路径
	@Value("${solr_client}")
	public String solr_client;
	// 手机端solr服务器地址
	@Value("${solrURL}")
	public String solrURL;

}
