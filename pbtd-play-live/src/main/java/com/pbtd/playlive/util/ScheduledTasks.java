package com.pbtd.playlive.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pbtd.playlive.service.impl.RedisServiceImpl;
import com.pbtd.playlive.util.getDate.GetAutomaticData;
import com.pbtd.playlive.util.getDate.GetManualData;

@Component
@Configurable
@EnableScheduling
@PropertySource("classpath:constant.properties") 
public class ScheduledTasks{
	@Autowired
	private  JsonServlet jsonServlet;

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

	@Autowired
	private GetAutomaticData getAllData;
	@Autowired
	private GetManualData getManualData;
	@Autowired
	private RedisServiceImpl redisServiceImpl;

	@Scheduled(cron = "${package_cron}")
	public void selectPackage() throws JSONException{
		logger.info("开始从中心获取直播package信息" + dateFormat().format (new Date()));
		getAllData.getPackage();
	}
	//每19分钟执行一次
	//@Scheduled(cron = "0 */19 *  * * * ")
	@Scheduled(cron = "${video_cron}")
	public void selectVideo() throws JSONException{
		logger.info("开始从中心获取直播video信息" + dateFormat().format (new Date()));
		getAllData.getVideo();
	}
	//每天执行一次
	@Scheduled(cron = "${otherepg_cron}")
	//   @Scheduled(cron = "0 51 19 * * ?")
	public void selectProgram() throws JSONException{
		logger.info("开始从中心获取直播program信息" + dateFormat().format (new Date()));
		getAllData.getProgram();
	}
	//每天执行一次
	@Scheduled(cron = "${cibnepg_cron}")
	public void selectCibnEpg() throws JSONException{
		logger.info("开始从中心获取国广节目单信息" + dateFormat().format (new Date()));
		getAllData.getCibnEpg();
	}

	//每天凌晨两点删除执行一次
	@Scheduled(cron = " 0 30 3 * * ?")
	public void delete() throws JSONException{
		logger.info("开始从删除直播数据库失效数据：五天前的数据情空" + dateFormat().format (new Date()));
		getAllData.deleteAll();
	}

	//每秒15s执行一次
	@Scheduled(cron="${busy_schedule}")
	public void getCibnEpgeasy() throws JSONException{
		logger.info("开始从国广接口获取当前和下一个信息：" + dateFormat().format (new Date()));
			boolean flag = jsonServlet.getJsonObjectNowEpg();
			if(flag){
				logger.info("更新EPG信息成功" + dateFormat().format (new Date()));
			}else{
				logger.info("更新EPG信息失败" + dateFormat().format (new Date()));
			}
	}

	private SimpleDateFormat dateFormat(){
		return new SimpleDateFormat ("HH:mm:ss");
	}

}
