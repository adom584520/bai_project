package com.pbtd.manager.vod.phone.search.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.manager.conf.MQInterfaceBeanConfig;
import com.pbtd.manager.util.ActivemqUtils;
import com.pbtd.manager.util.RedisClient;

/**
 * @author czx
 */
@Controller
@RequestMapping("/vod/phone/search")
@SuppressWarnings("all")
public class SearchPhoneController {

	public static Logger log = Logger.getLogger(SearchPhoneController.class);

	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = { "/index", "" })
	public String index() {
		return "/vod/phone/search/index";
	}

	// 导入全部数据到索引库
	@RequestMapping("/Index/indexImport")
	public String indexImport(Model model) throws Exception {
		String flag = "1";
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(MQInterfaceBeanConfig.activeMQTCP, MQInterfaceBeanConfig.topic_Name, "1",MQInterfaceBeanConfig.activemq_Flag);
			// ActivemqUtils.testQueueProducer(activemqTCP, topicName, "1");
			log.info("phone添加索引");
		} catch (Exception e) {
			flag = "0";
			log.error("phone添加索引失败:" + e);
		}
		model.addAttribute("flag", flag);
		return "/vod/phone/search/index";
	}

	// 删除索引
	@RequestMapping("/Delete/deleteIndex")
	public String deleteDocumentByQuery() {
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(MQInterfaceBeanConfig.activeMQTCP, MQInterfaceBeanConfig.topic_Name, "0",MQInterfaceBeanConfig.activemq_Flag);
			// ActivemqUtils.testQueueProducer(activemqTCP, topicName, "0");
			log.info("phone删除索引");
		} catch (Exception e) {
			log.error("phone删除索引失败: " + e);
		}
		return "/vod/phone/search/index";
	}

	// 优化索引
	@RequestMapping("/Optimize/IndexOptimize")
	public String optimizeIndex() {
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(MQInterfaceBeanConfig.activeMQTCP, MQInterfaceBeanConfig.topic_Name, "2",MQInterfaceBeanConfig.activemq_Flag);
			// ActivemqUtils.testQueueProducer(activemqTCP, topicName, "2");
			log.info("phone优化索引");
		} catch (Exception e) {
			log.error("phone优化索引失败: " + e);
		}
		return "/vod/phone/search/index";
	}

	// 导入全部专辑标题数据到索引库
	@RequestMapping("/Index/titleIndexImport")
	public String titleIndexImport(Model model) throws Exception {
		String flag = "1";
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(MQInterfaceBeanConfig.activeMQTCP, MQInterfaceBeanConfig.topic_Name, "3",MQInterfaceBeanConfig.activemq_Flag);
			// ActivemqUtils.testQueueProducer(activemqTCP, topicName, "3");
			log.info("添加标题索引 ");
		} catch (Exception e) {
			flag = "0";
			log.error("删除标题索引phone失败: " + e);
		}
		model.addAttribute("flag", flag);
		return "/vod/phone/search/index";
	}

	// 删除标题索引
	@RequestMapping("/Delete/deleteTitleIndex")
	public String deleteTitleIndex() {
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(MQInterfaceBeanConfig.activeMQTCP, MQInterfaceBeanConfig.topic_Name, "4",MQInterfaceBeanConfig.activemq_Flag);
			// ActivemqUtils.testQueueProducer(activemqTCP, topicName, "4");
			log.info("删除标题索引phone");
		} catch (Exception e) {
			log.error("删除标题索引phone失败: " + e);
		}
		return "/vod/phone/search/index";
	}

	// 优化标题索引
	@RequestMapping("/Optimize/titleIndexOptimize")
	public String titleIndexOptimize() {
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(MQInterfaceBeanConfig.activeMQTCP, MQInterfaceBeanConfig.topic_Name, "5",MQInterfaceBeanConfig.activemq_Flag);
			// ActivemqUtils.testQueueProducer(activemqTCP, topicName, "5");
			log.info("优化标题索引phone");
		} catch (Exception e) {
			log.error("优化标题索引phone失败: " + e);
		}
		return "/vod/phone/search/index";
	}

	// 通过ID添加索引
	public void phoneIndexAdd(String ids) {
		// phoneDelRedisKey("1011267,1011268,1011272,");
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(MQInterfaceBeanConfig.activeMQTCP, MQInterfaceBeanConfig.topicName_ID, ids,MQInterfaceBeanConfig.activemq_Flag);
			// ActivemqUtils.testQueueProducer(activemqTCP, topicNameID, ids);
			log.info("通过ID添加索引phone");
		} catch (Exception e) {
			log.error("通过ID添加索引phone失败: " + e);
		}
	}

	// 通过key删除redis缓存
	/*public void phoneDelRedisKey(String id) {
		try {
			String[] ids = id.split(",");
			RedisClient redis = new RedisClient();
			log.info("通过ID删除缓存phone");
			for (String string : ids) {
				// 根据key删除缓存
				redis.delKey("p" + string);
			}
		} catch (Exception e) {
			log.error("通过ID删除缓存失败phone: " + e);
		}
	}*/

	// 删除redis所有缓存
//	@RequestMapping("/redis/phoneDelRedis")
//	public String phoneDelRedis() {
//		try {
//			RedisClient redis = new RedisClient();
//			// 清空缓存
//			log.info(" 删除redis所有缓存phone: " + redis.del("p"));
//		} catch (Exception e) {
//			log.error("删除redis所有缓存phone失败: " + e);
//		}
//		return "/vod/phone/search/index";
//	}
}
