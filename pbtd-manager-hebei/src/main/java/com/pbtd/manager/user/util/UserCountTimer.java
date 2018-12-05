package com.pbtd.manager.user.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pbtd.manager.user.service.SysCountService;

/**
 * 定时任务 获取用户统计数据
 * 
 * @author admin
 *
 */
@Component
@Configurable
@EnableScheduling
@PropertySource(value = { "classpath:config/all_count.properties" })
public class UserCountTimer {
	public static Logger log = Logger.getLogger(UserCountTimer.class);

	@Autowired
	private SysCountService sysCountService;
//	@Async
//	@Scheduled(cron = "0/3 * * * * ?")	
//	public void testschedule() {
//		System.out.println("\n\n\n*********testschedule***********" + new Date() +  "\n\n\n");
////		try {
////			Thread.sleep(10*1000);
////		} catch (InterruptedException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//	}
	// 1点30更新用户统计数据
	@Async
	@Scheduled(cron = "${cron_usercount}")
	public void usercount() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>1：31 开始添加昨日用户统计");
		log.info("1：31 开始添加昨日用户统计");
		try {
			Date now = new Date();
			String today = DateFormaUtil.todayString(now)+" 01:31:00";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time = null;
			try {
				time = sdf.parse(today);
			} catch (ParseException e) {
				log.info("~~~~~~~~~~~~~~~~~~~~~~~昨日用户统计转换时间异常~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				time = now;
				e.printStackTrace();
			}
			sysCountService.usercount(time);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		log.info("~~~~~~~~~~~~~~~~~~~~~~~昨日用户统计结束~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	// 1点30更新专辑统计数据
	@Async
	@Scheduled(cron = "${cron_seriescount}")
	public void seriescount() {
		log.info("1：41 开始添加昨日专辑统计");
		try {
			Date now = new Date();
			String today = DateFormaUtil.todayString(now)+" 01:41:00";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time = null;
			try {
				time = sdf.parse(today);
			} catch (ParseException e) {
				log.info("~~~~~~~~~~~~~~~~~~~~~~~昨日专辑统计转换时间异常~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				time = now;
				e.printStackTrace();
			}
			sysCountService.seriescount(time);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		log.info("~~~~~~~~~~~~~~~~~~~~~~~昨日专辑统计结束~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	// 1点30更新专辑统计数据
	@Async
	@Scheduled(cron = "${cron_userkeep}")
	public void userKeepPercent(){
		Date date = new Date();
		Map<String,Object> queryParams = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		queryParams.put("endTime", sdf.format(DateFormaUtil.yesterday(date)));
		sysCountService.insertUserKeep(queryParams);

	}

}