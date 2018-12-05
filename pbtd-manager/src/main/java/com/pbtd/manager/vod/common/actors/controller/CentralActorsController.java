package com.pbtd.manager.vod.common.actors.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.util.URLSend;
import com.pbtd.manager.vod.common.actors.domain.Vodactors;
import com.pbtd.manager.vod.common.actors.service.face.IVodactorsService;

import net.sf.json.JSONObject;

/**
 * 汇聚下发运营平台
 * 
 * @author 程
 *
 */
@Controller
@RequestMapping("/integrate/outputjson")
@PropertySource(value = { "classpath:config/central.properties" })
public class CentralActorsController {
	public static Logger log = Logger.getLogger(CentralActorsController.class);

	@Value("${central_actors}")
	private String centralActors;
	@Autowired
	private IVodactorsService vodactors;

	// integrate/outputjson/phonealbumsinfo 专辑详情
	// integrate/outputjson/phonealbumsinfovideo 子集
	// integrate/outputjson/getphonechannel 手机频道
	// integrate/outputjson/getphonelabel 手机标签
	// integrate/outputjson/getactors 演员
	// 汇聚下发时触发这个接口

	// 手机频道添加
	@RequestMapping("/central/getactors")
	public void phonechannel(HttpServletRequest request) {
		log.info("从汇聚平台获取演员...");
		JSONObject json = new JSONObject();
		// 请求地址
		String rtsp = URLSend.sendHttpClientGet(centralActors);
		json = JSONObject.fromObject(rtsp);
		List<Vodactors> testList = JSON.parseArray(json.getJSONArray("body").toString(), Vodactors.class);
		for (Vodactors vodactor : testList) {
			log.info("演员添加: " + vodactor.getName());
			try {
				vodactors.insert(vodactor);
			} catch (Exception e) {
				log.info("演员添加异常: " + vodactor.getCode());
				continue;
			}
		}

	}
}
