package com.pbtd.launcher.controller;

import java.util.Collections;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.pbtd.launcher.annotation.LogAnnotation;
import com.pbtd.launcher.conf.ConstantBeanConfig;
import com.pbtd.launcher.domain.Advertisement;
import com.pbtd.launcher.domain.LauncherVersion;
import com.pbtd.launcher.dto.AdvertisementDTO;
import com.pbtd.launcher.service.AdvertisementService;
import com.pbtd.launcher.service.LauncherVersionService;
import com.pbtd.launcher.util.LauncherConstant;

@RestController
@RequestMapping("/tv/launcher")
public class AdvertisementController {
	private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private ConstantBeanConfig constant;
	@Autowired
	private LauncherVersionService launcherVersionService;

	@SuppressWarnings("unchecked")
	@LogAnnotation(operationInfo = "launcher_开机启动广告接口")
	@RequestMapping(value = "/getStartAdvertisementByGroupoId", params = { "proj_id", "groupId" })
	public HashMap<String, Object> getStartAdvertisement(HttpServletRequest request) {
		HashMap<String, Object> json = new HashMap<>();
		String proj_id = null;
		Long groupId = null;
		try {
			proj_id = request.getParameter("proj_id");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "proj_id参数类型错误！");
			json.put("data", "{}");
			return json;
		}
		try {
			groupId = Long.valueOf(request.getParameter("groupId"));
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "groupId参数类型错误！");
			json.put("data", "{}");
			return json;
		}
		if (proj_id == null || groupId == null) {
			json.put("code", 0);
			json.put("message", "参数不能为空！");
			json.put("data", "{}");
			return json;
		}
		if (!constant.projId.equals(proj_id)) {
			json.put("code", 0);
			json.put("message", "proj_id错误！");
			json.put("data", "{}");
			return json;
		}
		try {
			Advertisement adv = advertisementService.queryStartAdvByGroupId(groupId);
			if (adv != null) {
				AdvertisementDTO advDTO = new AdvertisementDTO();
				advDTO.setId(adv.getId());
				advDTO.setAdvName(adv.getAdvName());
				advDTO.setAdvTime(adv.getAdvTime());
				advDTO.setGroupId(adv.getGroupId());
				String images = adv.getImages();
				if (images != null && images.length() > 0) {
					advDTO.setImageList(JSON.parseArray(images, String.class));
				} else {

					advDTO.setImageList(Collections.EMPTY_LIST);
				}
				advDTO.setShowType(adv.getShowType());
				advDTO.setStatus(adv.getStatus());
				advDTO.setType(adv.getType());
				advDTO.setVideo(adv.getVideo());
				LauncherVersion version = launcherVersionService.queryByType(groupId,
						LauncherConstant.LAUNCHER_VERSION_TYPE_START_ADV);
				advDTO.setVersion(version.getVersion());
				json.put("code", 1);
				json.put("message", "响应成功！");
				json.put("data", advDTO);
			} else {
				json.put("code", 0);
				json.put("message", "无数据！");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "数据查询出错！");
			json.put("data", "{}");
			logger.error("launcher获取launcher启动广告信息-方法名称：getLauncherAdvertisement;", e);
		}
		return json;
	}

	@SuppressWarnings("unchecked")
	@LogAnnotation(operationInfo = "launcher_launcher启动广告接口")
	@RequestMapping(value = "/getLauncherAdvertisementByGroupId", params = { "proj_id", "groupId" })
	public HashMap<String, Object> getLauncherAdvertisement(HttpServletRequest request) {
		HashMap<String, Object> json = new HashMap<>();
		String proj_id = null;
		Long groupId = null;
		try {
			proj_id = request.getParameter("proj_id");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "proj_id参数类型错误！");
			json.put("data", "{}");
			return json;
		}
		try {
			groupId = Long.valueOf(request.getParameter("groupId"));
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "groupId参数类型错误！");
			json.put("data", "{}");
			return json;
		}
		if (proj_id == null || groupId == null) {
			json.put("code", 0);
			json.put("message", "参数不能为空！");
			json.put("data", "{}");
			return json;
		}
		if (!constant.projId.equals(proj_id)) {
			json.put("code", 0);
			json.put("message", "proj_id错误！");
			json.put("data", "{}");
			return json;
		}
		try {
			Advertisement adv = advertisementService.queryLauncherAdvByGroupId(groupId);
			if (adv != null) {
				AdvertisementDTO advDTO = new AdvertisementDTO();
				advDTO.setId(adv.getId());
				advDTO.setAdvName(adv.getAdvName());
				advDTO.setAdvTime(adv.getAdvTime());
				advDTO.setGroupId(adv.getGroupId());
				String images = adv.getImages();
				if (images != null && images.length() > 0) {
					advDTO.setImageList(JSON.parseArray(images, String.class));
				} else {

					advDTO.setImageList(Collections.EMPTY_LIST);
				}
				advDTO.setShowType(adv.getShowType());
				advDTO.setStatus(adv.getStatus());
				advDTO.setType(adv.getType());
				advDTO.setVideo(adv.getVideo());
				LauncherVersion version = launcherVersionService.queryByType(groupId,
						LauncherConstant.LAUNCHER_VERSION_TYPE_START_LAUNCHER);
				advDTO.setVersion(version.getVersion());
				json.put("code", 1);
				json.put("message", "响应成功！");
				json.put("data", advDTO);
			} else {
				json.put("code", 0);
				json.put("message", "无数据！");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "数据查询出错！");
			json.put("data", "{}");
			logger.error("launcher获取launcher启动广告信息-方法名称：getLauncherAdvertisement;", e);
		}
		return json;
	}
}
