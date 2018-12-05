package com.pbtd.manager.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pbtd.manager.vod.page.controller.PhoneJsonInterfaceController;

/**
 * 定时任务
 * 
 * @author admin
 *
 */
@Component
public class MyTimer {
	 

	 
	@Autowired
	private PhoneJsonInterfaceController phonejson;

	  
	 @Scheduled(cron = "0 */60 * * * ?")
	public void aikanseriesname() {
		phonejson.aikanseriesname(null, null);
	}

	 
}