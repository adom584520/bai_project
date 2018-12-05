package com.pbtd.playuser.web.controller;

import java.net.URLDecoder;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.pbtd.playuser.component.ConstantBeanConfig;
import com.pbtd.playuser.domain.VodCollectInfo;
import com.pbtd.playuser.dto.SeriesDTO;
import com.pbtd.playuser.dto.SeriesResultBean;
import com.pbtd.playuser.exception.JsonMessageException;
import com.pbtd.playuser.page.CollectHistoryQuery;
import com.pbtd.playuser.service.VodCollectInfoPhoneService;
import com.pbtd.playuser.util.JsonDateValueProcessor;
import com.pbtd.playuser.util.sendPost.HttpClientUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@RestController
@RequestMapping("/phone/user")
public class VodCollectInfoPhoneController {
	private static final Logger logger = LoggerFactory.getLogger(VodCollectInfoPhoneController.class);
	@Autowired
	private VodCollectInfoPhoneService vodCollectInfoService;
	@Autowired
	private ConstantBeanConfig constant;

	@RequestMapping(value = "/insertVodCollectinfo", params = { "userId", "seriesCode", "dramaname"})
	public String collect(final VodCollectInfo collectInfo) {
		JSONObject json = new JSONObject();
		try {
			collectInfo.setDramaname(URLDecoder.decode(collectInfo.getDramaname(), "utf-8"));
			collectInfo.setUserId(URLDecoder.decode(collectInfo.getUserId(), "utf-8"));
			vodCollectInfoService.insert(collectInfo);
			json.put("code", 1);
			json.put("message", "添加成功");
		} catch (Exception e) {
			json.put("message", "添加收藏失败！");
			json.put("code", 0);
			logger.error("用户系统-点播添加收藏接口-手机-添加失败！", e);
		}
		new Thread() {
			public void run() {
				HashMap<String, String> params = new HashMap<>();
				String seriesObj = null;
				params.put("seriesCode", String.valueOf(collectInfo.getSeriesCode()));
				logger.info("用户系统-点播添加收藏接口-手机-通过专辑ID调用接口获取专辑参数");
				seriesObj = HttpClientUtil.doPost(constant.querySeriesPhoneUrl, params, constant.charset);
				logger.info("响应的参数："+seriesObj);
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
					collectInfo.setPictureurl1(data.getPictureurl1());
					collectInfo.setPictureurl2(data.getPictureurl2());
					collectInfo.setDuration(data.getDuration());
					collectInfo.setViewPoint(data.getViewPoint());
					collectInfo.setSeriesName(data.getSeriesName());
					vodCollectInfoService.update(collectInfo);
				} catch (Exception e) {
					logger.error("用户系统-点播添加收藏接口-手机-查询专辑后重新保存失败！", e);
				}
			}
		}.start();
		return json.toString();
	}

	@RequestMapping(value = "/deleteVodCollectinfo", params = { "userId" })
	public String delete(String userId, String seriesCode) {
		JSONObject json = new JSONObject();
		try {
			vodCollectInfoService.delete(userId, seriesCode);
			json.put("message", "删除成功！");
			json.put("code", 1);
		} catch (JsonMessageException e) {
			json.put("message", e.getMessage());
			json.put("code", 0);
			logger.error("用户系统-点播删除收藏接口-手机-删除失败！", e);
		} catch (Exception e) {
			json.put("message", "删除失败！");
			json.put("code", 0);
			logger.error("用户系统-点播删除收藏接口-手机-删除失败！", e);
		}
		return json.toString();
	}

	@RequestMapping(value = "/getVodCollectinfoList", params = { "userId" })
	public String queryAll(CollectHistoryQuery chq) {
		logger.info("开始调用getVodCollectinfoList接口，开始时间"+System.currentTimeMillis());
		HashMap<String, Object> json = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject jsonObject = new JSONObject();
		try {
			List<VodCollectInfo> data = vodCollectInfoService.queryAll(chq);
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
			logger.error("用户系统-点播查询收藏接口-手机-查询失败！", e);
		} catch (Exception e) {
			json.put("message", "查询失败！");
			json.put("code", 0);
			json.put("data", Collections.EMPTY_LIST);
			logger.error("用户系统-点播查询收藏接口-手机-查询失败！", e);
		}
		jsonObject.putAll(json, jsonConfig);
		logger.info("调用getVodCollectinfoList接口完成，结束时间"+System.currentTimeMillis());
		return jsonObject.toString();
	}

	@RequestMapping(value = "/getIsCollect", params = { "userId", "seriesCode" })
	public String queryIsCollect(String userId, Integer seriesCode) {
		JSONObject json = new JSONObject();
		try {
			boolean flag = vodCollectInfoService.queryIsCollect(userId, seriesCode);
			json.put("message", "查询成功！");
			json.put("code", flag ? 1 : 0);
		} catch (JsonMessageException e) {
			json.put("message", e.getMessage());
			json.put("code", 0);
			logger.error("用户系统-点播查询是否收藏接口-手机-查询失败！", e);
		} catch (Exception e) {
			json.put("message", "查询失败！");
			json.put("code", 0);
			logger.error("用户系统-点播查询是否收藏接口-手机-查询失败！", e);
		}
		return json.toString();
	}
}
