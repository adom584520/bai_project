package com.pbtd.manager.conf;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pbtd.playclick.youku.controller.JsonYoukuContrlller;
import com.pbtd.playclick.youku.domain.staticmap;

@Component
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(InitListener.class);
	@Autowired
	private staticmap staticmap;
	@Autowired
	private JsonYoukuContrlller jsonyouku;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("初始化静态常量值......");
		staticmap.setAc_list();
		staticmap.setChn_list();
		staticmap.setLab_list();
		//初始化定时任务启动监控
		Map<String, Object> m=new HashMap<String, Object>();
			m.put("type", "youku_mediamessagenew");
			m.put("status", "0");
			jsonyouku.update_quarzt(m);
			m.put("type", "youku_mediamessagenew_start");
			m.put("status", "0");
			jsonyouku.update_quarzt(m);
		LOGGER.info("初始化数据成功！");
	}
}