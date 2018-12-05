package com.pbtd.manager.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
@PropertySource(value = { "classpath:config/redis-vod.properties" })
public class RedisConfValue {

	public static  String IP = "1.1.1.1"; 
	public static  String PORT = "6379";         // 端口
	public static  String AUTH="123456"; 

    @Value("${vod.redis.host}")
    public void setHTTP_IP(String http_ip) {
    	IP = http_ip;
	}

    @Value("${vod.redis.port}")
	public  void setPROJ_ID(String proj_id) {
    	PORT = proj_id;
	}

	@Value("${vod.redis.password}")
	public void setPICTURE_IP(String picture_ip) {
		AUTH = picture_ip;
	}

}
