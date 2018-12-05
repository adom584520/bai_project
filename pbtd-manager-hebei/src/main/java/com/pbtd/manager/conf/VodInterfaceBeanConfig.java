package com.pbtd.manager.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
@PropertySource(value = { "classpath:config/vodinterface.properties" })
public class VodInterfaceBeanConfig {
	public  static String TV_ONLINE_URL;
	public  static String PHONE_ONLINE_URL;
	public  static String LOCALHOST_URL;

    @Value("${tv_online_url}")
    public void setTV_ONLINE_URL(String tv_online_url) {
    	TV_ONLINE_URL = tv_online_url;
	}

    @Value("${phone_online_url}")
	public  void setPHONE_ONLINE_URL(String phone_online_url) {
    	PHONE_ONLINE_URL = phone_online_url;
	}
    @Value("${localhost_url}")
    public  void setLOCALHOST_URL(String localhost_url) {
    	LOCALHOST_URL = localhost_url;
    }

}
