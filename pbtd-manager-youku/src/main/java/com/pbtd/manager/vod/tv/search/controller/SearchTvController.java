package com.pbtd.manager.vod.tv.search.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pbtd.manager.util.ActivemqUtils;
import com.pbtd.manager.util.RedisClient;

/**
 * @author czx
 */
@Controller
@RequestMapping("/vod/tv/search")
@SuppressWarnings("all")
@PropertySource(value = { "classpath:config/activemq.properties" })
public class SearchTvController {

	public static Logger log = Logger.getLogger(SearchTvController.class);

	// Activemq地址
	@Value("${activemqTCP}")
	private String activemqTCP;
	// TV点对点发送名称
	@Value("${queueNameTV}")
	private String queueNameTV;
	// TV广播发送名称
	@Value("${topicNameTV}")
	private String topicNameTV;
	// TV广播专辑id发送名称
	@Value("${topicNameTVid}")
	private String topicNameTVid;

	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = { "/index", "" })
	public String index() {
		return "/vod/tv/search/index";
	}

	// TV导入全部数据到索引库
	@RequestMapping("/Index/indexImport")
	public String indexImport(Model model) throws Exception {
		String flag = "1";
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(activemqTCP, topicNameTV, "1");
			// ActivemqUtils.testQueueProducer(activemqTCP, topicNameTV, "1");
			log.info("TV添加索引 ");
		} catch (Exception e) {
			flag = "0";
			log.error("TV添加索引失败:" + e);
		}
		model.addAttribute("flag", flag);
		return "/vod/tv/search/index";
	}

	// TV删除索引
	@RequestMapping("/Delete/deleteIndex")
	public String deleteDocumentByQuery() {
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(activemqTCP, topicNameTV, "0");
			// ActivemqUtils.testQueueProducer(activemqTCP, topicNameTV, "0");
			log.info("TV删除索引");
		} catch (Exception e) {
			log.error("TV删除索引失败: " + e);
		}
		return "/vod/tv/search/index";
	}

	// TV优化索引
	@RequestMapping("/Optimize/IndexOptimize")
	public String optimizeIndex() {
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(activemqTCP, topicNameTV, "2");
			// ActivemqUtils.testQueueProducer(activemqTCP, topicNameTV, "2");
			log.info("TV优化索引");
		} catch (Exception e) {
			log.error("TV优化索引失败: " + e);
		}
		return "/vod/tv/search/index";
	}

	// TV导入全部专辑标题数据到索引库
	@RequestMapping("/Index/titleIndexImport")
	public String titleIndexImport(Model model) throws Exception {
		String flag = "1";
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(activemqTCP, topicNameTV, "3");
			// ActivemqUtils.testQueueProducer(activemqTCP, topicNameTV, "3");
			log.info("TV添加标题索引");
		} catch (Exception e) {
			flag = "0";
			log.error("TV添加标题索引失败:" + e);
		}
		model.addAttribute("flag", flag);
		return "/vod/tv/search/index";
	}

	// TV删除标题索引
	@RequestMapping("/Delete/deleteTitleIndex")
	public String deleteTitleIndex() {
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(activemqTCP, topicNameTV, "4");
			// ActivemqUtils.testQueueProducer(activemqTCP, topicNameTV, "4");
			log.info("TV删除标题索引");
		} catch (Exception e) {
			log.error("TV删除标题索引: " + e);
		}
		return "/vod/tv/search/index";
	}

	// TV优化标题索引
	@RequestMapping("/Optimize/titleIndexOptimize")
	public String titleIndexOptimize() {
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(activemqTCP, topicNameTV, "5");
			// ActivemqUtils.testQueueProducer(activemqTCP, topicNameTV, "5");
			log.info("TV优化标题索引");
		} catch (Exception e) {
			log.error("TV优化标题索引失败: " + e);
		}
		return "/vod/tv/search/index";
	}

	// 通过ID添加索引
	public void tvIndexAdd(String ids) {
		// tvDelRedisKey("1004287,1011272,1011267,");
		try {
			// 通知activemq
			ActivemqUtils.testTopicProducer(activemqTCP, topicNameTVid, ids);
			// ActivemqUtils.testQueueProducer(activemqTCP, topicNameTVid, ids);
			log.info("通过ID添加索引tv");
		} catch (Exception e) {
			log.error("通过ID添加索引tv失败: " + e);
		}
	}

	// 通过key删除redis缓存

	public void tvDelRedisKey(String id) {
		try {
			String[] ids = id.split(",");
			RedisClient redis = new RedisClient();
			log.info("通过ID删除缓存tv");
			for (String string : ids) {
				// 根据key删除缓存
				redis.delKey("t" + string);
			}
		} catch (Exception e) {
			log.error("通过ID删除缓存失败tv: " + e);
		}
	}

	// 删除redis所有缓存
	@RequestMapping("/redis/tvDelRedis")
	public String tvDelRedis(String ids) {
		try {
			RedisClient redis = new RedisClient();
			// 清空缓存
			log.info(" 删除redis所有缓存tv: " + redis.del("t"));
		} catch (Exception e) {
			log.error("删除redis所有缓存tv失败: " + e);
		}
		return "/vod/tv/search/index";
	}
}
