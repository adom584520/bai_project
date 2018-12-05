package com.pbtd.manager.live.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.manager.util.ActivemqUtils;

/**
 * @author zr
 */
@Controller
@Component
@PropertySource(value = { "classpath:config/activemq.properties" })
public class SearchLiveController {

	public static Logger log = Logger.getLogger(SearchLiveController.class);

	// Activemq地址
	@Value("${activemqTCP}")
	private String activemqTCP;
	// live直播数据发送名称
	@Value("${topicNameLive}")
	private String topicNameLive;
	// live直播数据删除发送名称
	@Value("${topicNameLiveDel}")
	private String topicNameLiveDel;

	// live通过ID添加索引
	public void liveIndexAdd(String ids) throws Exception {
		// 通知activemq
		ActivemqUtils.testTopicProducer(activemqTCP, topicNameLive, ids);
		// ActivemqUtils.testQueueProducer(activemqTCP, topicNameLive, ids);
		log.info("通过ID添加索引live");
	}

	// live删除标题索引
	public void deleteLiveIndex(String ids) throws Exception {
		ActivemqUtils.testTopicProducer(activemqTCP, topicNameLiveDel, ids);
		// 通知activemq
		// ActivemqUtils.testQueueProducer(activemqTCP, topicNameLiveDel, ids);
		log.info("live删除标题索引");
	}
}
