package com.pbtd.playuser.web.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbtd.playuser.component.ConstantBeanConfig;
import com.pbtd.playuser.domain.PlayHistoryInfo;
import com.pbtd.playuser.exception.JsonMessageException;
import com.pbtd.playuser.page.CollectHistoryQuery;
import com.pbtd.playuser.service.PlayHistoryInfoPhoneService;
import com.pbtd.playuser.util.JsonDateValueProcessor;
import com.pbtd.playuser.util.JsonMessage;
import com.pbtd.playuser.util.RequestUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@RestController
@RequestMapping("/phone/user")
public class PlayHistoryInfoPhoneController {
	private static final Logger logger = LoggerFactory.getLogger(PlayHistoryInfoPhoneController.class);

	private final static String ENCODE = "UTF-8"; 
	@Autowired
	private PlayHistoryInfoPhoneService playHistoryInfoService;
	@Autowired
	private ConstantBeanConfig constant;

 
	@RequestMapping(value = "/insertPlayHistoryInfo")
	public JsonMessage insert(HttpServletRequest request) {
		logger.info("******************************insertPlayHistoryInfo接口start*********************************");
		Map<String, Object> queryParams = RequestUtil.asMaptoLowerCase(request, false);
		final String SeriesCode = (String) queryParams.get("seriescode"); 
		String channel = (String) queryParams.get("channelcode"); 
		String  seriesName = (String)queryParams.get("seriesname");
		String  channelname = (String)queryParams.get("channelname");
		String  dramaname = (String)queryParams.get("dramaname");
		String  dram = (String)queryParams.get("drama");
		Long playLength = (Long)queryParams.get("playLength");
		playLength = playLength != null ? playLength : 0;
		//如果数据异常，就默认存0
		if(playLength >= 1000000000){
			playLength = 0L;
		}
		//处理中文字符
		try {
			seriesName = java.net.URLDecoder.decode(seriesName, ENCODE);
			channelname = java.net.URLDecoder.decode(channelname, ENCODE);
			dramaname = java.net.URLDecoder.decode(dramaname, ENCODE);
		} catch (Exception e) {
			logger.info("存在中文参数不存在情况");
//			e.printStackTrace();
		};

		queryParams.put("seriesname", seriesName);
		queryParams.put("seriesName", seriesName);
		queryParams.put("channelname", channelname);
		queryParams.put("dramaname", dramaname);
		queryParams.put("channellist",channel);
		queryParams.put("dram",dram);
		queryParams.put("playLength",playLength);
		JsonMessage result = new JsonMessage();
	
		if(SeriesCode == null){
			result = new JsonMessage(2,"SeriesCode不能为空");
			logger.info("SeriesCode:  参数不存在");
			return result;
		}
		Long start = System.currentTimeMillis();
		logger.info("starttime:"+start);
		final int ID  = playHistoryInfoService.insert(queryParams);
		if(ID == -1){
			result = new JsonMessage(1,"添加失败");
			logger.error("用户系统-点播添加播放记录接口-手机-添加失败！");
		}else{
			result = new JsonMessage(1,"添加成功");
		}
		//		if(channel == null){
		//			new Thread() {
		//				public void run() {
		//					HashMap<String, Object> params = new HashMap<>();
		//					String seriesObj = null;
		//					params.put("seriesCode",SeriesCode);
		//					logger.info("用户系统-点播添加播放记录接口-手机-通过专辑ID调用接口获取专辑参数");
		//					seriesObj = HttpClientUtil.doPost(constant.querySeriesPhoneUrl, params, constant.charset);
		//					SeriesResultBean srb = null;
		//					SeriesDTO data = null;
		//					try {
		//						srb = JSON.parseObject(seriesObj, SeriesResultBean.class);
		//						if (srb == null) {
		//							throw new JsonMessageException("查询专辑出错！");
		//						}
		//						if (!(srb.getCode() != null && srb.getCode() == 1)) {
		//							throw new JsonMessageException("查询专辑出错！");
		//						}
		//						data = srb.getData();
		//						params.put("id", ID);
		//						params.put("pictureurl1", data.getPictureurl1());
		//						params.put("pictureurl2", data.getPictureurl2());
		//						params.put("duration", data.getDuration());
		//						params.put("viewpoint", data.getViewPoint());
		//						params.put("seriesname", data.getSeriesName());
		//						params.put("channellist", data.getChannellist());
		//						playHistoryInfoService.update(params);
		//					} catch (Exception e) {
		//						logger.error("用户系统-点播添加播放记录接口-手机-查询专辑后重新保存失败！", e);
		//					}
		//				}
		//			}.start();
		//		}

		Long end = System.currentTimeMillis();
		logger.info("endtime:"+(end-start));
		logger.info("******************************insertPlayHistoryInfo接口end*********************************");
		return result;
	}

	@RequestMapping(value = "/deletePlayHistoryInfo", params = { "userId" })
	public String updateStatus(String userId, String seriesCode) {
		JSONObject json = new JSONObject();
		try {
			playHistoryInfoService.updateStatus(userId, seriesCode);
			json.put("message", "删除成功！");
			json.put("code", 1);
		} catch (JsonMessageException e) {
			json.put("message", e.getMessage());
			json.put("code", 0);
			logger.error("用户系统-点播删除播放记录接口-手机-删除失败！", e);
		} catch (Exception e) {
			json.put("message", "删除失败！");
			json.put("code", 0);
			logger.error("用户系统-点播删除播放记录接口-手机-删除失败！", e);
		}
		return json.toString();
	}

	@RequestMapping(value = "/getPlayHistoryInfoList", params = { "userId" })
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
			logger.error("用户系统-点播查询播放记录接口-手机-查询失败！", e);
		} catch (Exception e) {
			json.put("message", "查询失败！");
			json.put("code", 0);
			json.put("data", Collections.EMPTY_LIST);
			logger.error("用户系统-点播查询播放记录接口-手机-查询失败！", e);
		}
		jsonObject.putAll(json, jsonConfig);
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getPlayHistoryInfoOne", params = { "userId", "seriesCode" })
	public String queryOne(String userId, Integer seriesCode, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject json = new JSONObject();
		try {
			PlayHistoryInfo data = playHistoryInfoService.queryOne(userId, seriesCode);
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
			logger.error("用户系统-点播查询播放记录接口-手机-查询失败！", e);
		} catch (Exception e) {
			map.put("message", "查询失败！");
			map.put("code", 0);
			map.put("data", "{}");
			logger.error("用户系统-点播查询播放记录接口-手机-查询失败！", e);
		}
		json.putAll(map, jsonConfig);
		return json.toString();
	}
}