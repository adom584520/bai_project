package com.pbtd.playclick.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pbtd.playclick.guoguang.controller.JsonContrlller;
import com.pbtd.playclick.yinhe.controller.QuartzController;
import com.pbtd.playclick.youku.controller.JsonYoukuContrlller;
import com.pbtd.playclick.youku.controller.YoukuStorageController;

/**
 * 定时任务
 * 
 * @author admin
 *
 */
@Component
public class MyTimer {
	public static Logger log = Logger.getLogger(MyTimer.class);
	@Autowired
	private JsonContrlller jsoncontrller;
	@Autowired
	private JsonYoukuContrlller jsonyouku;
	@Autowired
	private QuartzController quartzController;

	@Autowired
	private  YoukuStorageController youkuStorage;

	// 0点31更新 银河定时更新
	@Async
	@Scheduled(cron = "0 31 0  * * ?")
	public void timerRate6() {
		System.out.println("3点更新  银河定时更新");
		try {
			quartzController.syncAlbumsQuartz();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		System.out.println("银河更新结束 更新国广数据");
		try {
			jsoncontrller.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// jsonyouku.mediamessage();
		//  System.out.println("汇聚更新");
		/* try {
			 albumStrategyx.updateStrategalbum();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} */

	}
	@Async
	//@Scheduled(cron = "0 02 12  * * ?")
	public void timerRate2() {
		try {
			this.quartzController.syncAlbumsQuartzs();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		/*System.out.println("汇聚更新");
		try {
			this.strategyxController.saveStrategyvod();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}


	@Async
	@Scheduled(cron = "0 0/30 * * * ?")
	public void youku() {
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("type", "youku_mediamessagenew");
		// 更新优酷增量数据  每半小时爬取一次
		Map<String, Object> map=	jsonyouku.find_quarzt(m);
		String status=map.get("status").toString();
		if(status.equals("0")){
			m.put("status", "1");
			jsonyouku.update_quarzt(m);
			try {
				jsonyouku.mediamessagenew();// 增量数据
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			m.put("status", "0");
			jsonyouku.update_quarzt(m);
		}
		
	}
	@Async
	@Scheduled(cron = "0 0/15 * * * ?")
	public void mediamessagenew_start() {
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("type", "youku_mediamessagenew_start");
		// 更新优酷增量数据  每半小时爬取一次
		Map<String, Object> map=	jsonyouku.find_quarzt(m);
		String status=map.get("status").toString();
		if(status.equals("0")){
			m.put("status", "1");
			jsonyouku.update_quarzt(m);
			// 更新优酷增量数据  每半小时爬取一次
			try {
				jsonyouku.mediamessagenew_start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			m.put("status", "0");
			jsonyouku.update_quarzt(m);
		}
		
	}

	//每2小时下发一次数据
	@Async
	@Scheduled(cron = "0 */120 * * * ?")
	public void timerR() {
		try {
			//爬取结束增量数据自动更新入库
			youkuStorage.saveyoukutime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	@Async
	@Scheduled(cron = "0 0/30 * * * ?")
	public void PlayPolicyMsgs() {
		// 更新优酷播控增量数据  每半小时爬取一次
		try {
			jsonyouku.getPlayPolicyMsgs();// 增量数据
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}