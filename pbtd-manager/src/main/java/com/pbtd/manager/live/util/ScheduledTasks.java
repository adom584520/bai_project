package com.pbtd.manager.live.util;

import java.text.ParseException;
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

import com.pbtd.manager.live.spider.LiveCibnEpgInterface;
import com.pbtd.manager.live.spider.LiveProgremByBaidu;
import com.pbtd.manager.live.spider.LiveProgremByInterface;

@Component
@Configurable
@EnableScheduling
@PropertySource(value = { "classpath:config/liveScheduled.properties" })
public class ScheduledTasks{

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	private LiveProgremByBaidu liveProgremByBaidu;
	@Autowired
	private LiveProgremByInterface liveProgremByInterface;
	@Autowired
	private LiveCibnEpgInterface liveCibnEpgInterface;
	@Autowired
	private DeleteAutoData deleteAutoData;

	@Scheduled(cron = "${baiduepg_cron}")
	public void reportCurrent(){
		logger.info("开始从百度网取直播节目单信息" + dateFormat ().format (new Date()));
		try {
			try {
				liveProgremByBaidu.ChncodeMans();
			} catch (ParseException e) {
				e.printStackTrace();
				logger.info("日期转换出错数据出错");
			}
		} catch (InterruptedException e) {
			logger.info("网站爬取 数据出错");
			e.printStackTrace();
		}
	}
	//每4天执行一次
	@Scheduled(cron = "${cibnepg_cron}")
	public void CibnEpgInter(){
		logger.info("开始从国广接口爬去取直播节目单信息" + dateFormat ().format (new Date()));
		try {
			liveCibnEpgInterface.ChncodeMans();
		} catch (InterruptedException e) {
			logger.info("网站爬取 数据出错");
			e.printStackTrace();
		}
	}
	//每4天执行一次
	@Scheduled(cron = "${otherepg_cron}")
	public void reportCurrents(){
		logger.info("开始从其他提供接口获取直播节目单信息" + dateFormat ().format (new Date()));
		try {
			liveProgremByInterface.ChncodeMansS();
		} catch (InterruptedException e) {
			logger.info("网站爬取 数据出错");
			e.printStackTrace();
		}
	}

	//每天凌晨两点删除执行一次
	@Scheduled(cron = " 0 0 3 * * ?")
	//   @Scheduled(cron = "0 48 15 * * ?")
	public void delete() throws JSONException{
		logger.info("开始从删除直播数据库失效数据：五天前的数据情空" + dateFormat().format (new Date()));
		deleteAutoData.deleteAll();
	}

	private SimpleDateFormat dateFormat(){
		return new SimpleDateFormat ("HH:mm:ss");
	}

}
