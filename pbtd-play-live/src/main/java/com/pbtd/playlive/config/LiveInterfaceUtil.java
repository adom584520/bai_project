package com.pbtd.playlive.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class LiveInterfaceUtil {

	public  static String HTTP_IP;
	public  static String PROJ_ID;
	public  static String PICTURE_IP;

    @Value("${http_ip}")
    public void setHTTP_IP(String http_ip) {
		HTTP_IP = http_ip;
	}

    @Value("${proj_id}")
	public  void setPROJ_ID(String proj_id) {
		PROJ_ID = proj_id;
	}

	@Value("${picture_ip}")
	public void setPICTURE_IP(String picture_ip) {
		PICTURE_IP = picture_ip;
	}

}
