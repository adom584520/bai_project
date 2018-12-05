package com.pbtd.playuser.web.controller;

import java.net.URLDecoder;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.pbtd.playuser.component.ConstantBeanConfig;
import com.pbtd.playuser.domain.PlayHistoryInfo;
import com.pbtd.playuser.dto.SeriesDTO;
import com.pbtd.playuser.dto.SeriesResultBean;
import com.pbtd.playuser.exception.JsonMessageException;
import com.pbtd.playuser.page.CollectHistoryQuery;
import com.pbtd.playuser.service.PlayHistoryInfoTVService;
import com.pbtd.playuser.util.JsonDateValueProcessor;
import com.pbtd.playuser.util.sendPost.HttpClientUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@RestController
@RequestMapping("/tv/user")
public class PlayHistoryInfoTVController {
	private static final Logger logger = LoggerFactory.getLogger(PlayHistoryInfoTVController.class);
	@Autowired
	private PlayHistoryInfoTVService playHistoryInfoService;
	@Autowired
	private ConstantBeanConfig constant;

	@RequestMapping(value = "/insertPlayHistoryInfo", params = { "mac", "seriesCode", "dramaname"})
	public String insert(final PlayHistoryInfo ph) {
		JSONObject json = new JSONObject();
		try {
			ph.setDramaname(URLDecoder.decode(ph.getDramaname(), "utf-8"));
			ph.setMac(URLDecoder.decode(ph.getMac(), "utf-8"));
			playHistoryInfoService.insert(ph);
			json.put("code", 1);
			json.put("message", "添加成功");
		} catch (Exception e) {
			json.put("message", "播放记录失败！");
			json.put("code", 0);
			logger.error("用户系统-点播添加播放记录接口-TV-添加失败！", e);
		}
		new Thread() {
			public void run() {
				HashMap<String, String> params = new HashMap<>();
				String seriesObj = null;
				params.put("seriesCode", String.valueOf(ph.getSeriesCode()));
				logger.info("用户系统-点播添加播放记录接口-TV-通过专辑ID调用接口获取专辑参数");
				seriesObj = HttpClientUtil.doPost(constant.querySeriesTvUrl, params, constant.charset);
				SeriesResultBean srb =null;
				SeriesDTO data = null;
				try {
					srb = JSON.parseObject(seriesObj, SeriesResultBean.class);
					if(srb==null){
						throw new JsonMessageException("查询专辑出错！");
					}
					if (!(srb.getCode() != null && srb.getCode() == 1)) {
						throw new JsonMessageException("查询专辑出错！");
					}
					data = srb.getData();
					ph.setPictureurl1(data.getPictureurl1());
					ph.setPictureurl2(data.getPictureurl2());
					ph.setDuration(data.getDuration());
					ph.setViewPoint(data.getViewPoint());
					ph.setSeriesName(data.getSeriesName());
					playHistoryInfoService.update(ph);
				} catch (Exception e) {
					logger.error("用户系统-点播添加播放记录接口-TV-查询专辑后重新保存失败！", e);
				}
			}
		}.start();
		return json.toString();
	}

	@RequestMapping(value = "/deletePlayHistoryInfo", params = { "mac" })
	public String updateStatus(String mac, String seriesCode) {
		JSONObject json = new JSONObject();
		try {
			playHistoryInfoService.updateStatus(mac, seriesCode);
			json.put("message", "删除成功！");
			json.put("code", 1);
		} catch (JsonMessageException e) {
			json.put("message", e.getMessage());
			json.put("code", 0);
			logger.error("用户系统-点播删除播放记录接口-TV-删除失败！", e);
		} catch (Exception e) {
			json.put("message", "删除失败！");
			json.put("code", 0);
			logger.error("用户系统-点播删除播放记录接口-TV-删除失败！", e);
		}
		return json.toString();
	}

	@RequestMapping(value = "/getPlayHistoryInfoList", params = { "mac" })
	public String queryAll(CollectHistoryQuery chq) {
		HashMap<String, Object> json = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject jsonObject = new JSONObject();
		try {
			List<PlayHistoryInfo> data = playHistoryInfoService.queryAll(chq);
			if (data != null && data.size() > 0) {
				json.put("message", "查询成功！");
				json.put("code", 1);
				json.put("data", data);
			} else {
				json.put("message", "无数据！");
				json.put("code", 0);
				json.put("data", Collections.EMPTY_LIST);
			}
		} catch (JsonMessageException e) {
			json.put("message", e.getMessage());
			json.put("code", 0);
			json.put("data", Collections.EMPTY_LIST);
			logger.error("用户系统-点播查询播放记录接口-TV-查询失败！", e);
		} catch (Exception e) {
			json.put("message", "查询失败！");
			json.put("code", 0);
			json.put("data", Collections.EMPTY_LIST);
			logger.error("用户系统-点播查询播放记录接口-TV-查询失败！", e);
		}
		jsonObject.putAll(json, jsonConfig);
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getPlayHistoryInfoOne", params = { "mac", "seriesCode" })
	public String queryOne(String mac, Integer seriesCode, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject json = new JSONObject();
		try {
			PlayHistoryInfo data = playHistoryInfoService.queryOne(mac, seriesCode);
			if (data != null) {
				map.put("message", "查询成功！");
				map.put("code", 1);
				map.put("data", data);
			} else {
				map.put("message", "无数据！");
				map.put("code", 0);
				map.put("data", "{}");
			}
		} catch (JsonMessageException e) {
			map.put("message", e.getMessage());
			map.put("code", 0);
			map.put("data", "{}");
			logger.error("用户系统-点播查询播放记录接口-TV-查询失败！", e);
		} catch (Exception e) {
			map.put("message", "查询失败！");
			map.put("code", 0);
			map.put("data", "{}");
			logger.error("用户系统-点播查询播放记录接口-TV-查询失败！", e);
		}
		json.putAll(map, jsonConfig);
		return json.toString();
	}
}