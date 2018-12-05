package com.pbtd.manager.live.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configurable
@EnableScheduling
@PropertySource(value = { "classpath:config/liveinterface.properties" })
public class ScheduledTasks{

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

	@Autowired
	private GetAutomaticData getAllData;

	@Scheduled(cron = "${package_cron}")
	public void selectPackage() throws JSONException{
		logger.info("开始从中心获取直播package信息" + dateFormat().format (new Date()));
		getAllData.getPackage();
	}
	
	@Scheduled(cron = "${video_cron}")
	public void selectVideo() throws JSONException{
		logger.info("开始从中心获取直播video信息" + dateFormat().format (new Date()));
		getAllData.getVideo();
	}

	@Scheduled(cron = "${cibnepg_cron}")
	public void selectCibnEpg() throws JSONException{
		logger.info("开始从中心获取国广节目单信息" + dateFormat().format (new Date()));
		getAllData.getCibnEpg();
	}
	
	@Scheduled(cron = "${otherepg_cron}")
	public void selectProgram() throws JSONException{
		logger.info("开始从中心获取直播program信息" + dateFormat().format (new Date()));
		getAllData.getProgram();
	}
	
	//每天凌晨两点删除执行一次
	@Scheduled(cron = " 0 00 3 * * ?")
	public void delete() throws JSONException{
		logger.info("开始从删除直播数据库失效数据：五天前的数据情空" + dateFormat().format (new Date()));
		getAllData.deleteAll();
	}

	private SimpleDateFormat dateFormat(){
		return new SimpleDateFormat ("HH:mm:ss");
	}

}
