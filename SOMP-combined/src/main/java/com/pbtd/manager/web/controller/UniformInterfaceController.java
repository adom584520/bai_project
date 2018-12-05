package com.pbtd.manager.web.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pbtd.manager.domain.CpAlbuminfo;
import com.pbtd.manager.domain.CpChannel;
import com.pbtd.manager.domain.CpDrama;
import com.pbtd.manager.dto.ImportAlbuminfo;
import com.pbtd.manager.dto.ImportAlbuminfoStatus;
import com.pbtd.manager.dto.ImportChannel;
import com.pbtd.manager.dto.ImportDrama;
import com.pbtd.manager.service.AlbuminfoService;
import com.pbtd.manager.service.CpAlbuminfoService;
import com.pbtd.manager.service.CpChannelService;
import com.pbtd.manager.service.CpDramaService;
import com.pbtd.manager.service.DramaService;
import com.pbtd.manager.service.UniformInterfaceService;
import com.pbtd.manager.util.AlbuminfoConstant;
import com.pbtd.manager.util.pushUtils;

@Controller
@RequestMapping("/common/combined")
public class UniformInterfaceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UniformInterfaceController.class);
	@Autowired
	private UniformInterfaceService UniformService;
	@Autowired
	private CpAlbuminfoService cpAlbuminfoService;
	@Autowired
	private CpDramaService cpDramaService;
	@Autowired
	private AlbuminfoService albuminfoService;
	@Autowired
	private DramaService dramaService;
	@Autowired
	private pushUtils pushUtil;
	@Autowired
	private CpChannelService cpChannelService;

	@RequestMapping("/common")
	@ResponseBody
	public String common(HttpServletRequest request) {
		HashMap<String, Object> json = new HashMap<>();
		String data = null;
		try {
			data = pushUtil.getrequest(request);
			LOGGER.info(data);
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "参数有误！");
			return JSON.toJSONString(json);
		}
		if (data == null || "".equals(data)) {
			json.put("code", 0);
			json.put("message", "参数有误！");
			return JSON.toJSONString(json);
		}
		try {
			JSONObject jsonObject = JSON.parseObject(data);
			String operation = jsonObject.getString("operation");
			JSONObject jsonObject2 = jsonObject.getJSONObject("params");
			switch (operation) {
			case "validataAlbum":
				return UniformService.validataAlbum(jsonObject2);
			case "validataChannel":
				return UniformService.validataChannel(jsonObject2);
			case "initVodPlayHistorys":
				return UniformService.initVodPlayHistorys(jsonObject2);
			case "initLiveFavorites":
				return UniformService.initLiveFavorites(jsonObject2);
			case "initVodFavorites":
				return UniformService.initVodFavorites(jsonObject2);
			case "uploadVodPlayHistorys":
				return UniformService.uploadVodPlayHistorys(jsonObject2);
			case "getVodPlayHistorys":
				return UniformService.getVodPlayHistorys(jsonObject2);
			case "uploadLiveFavorites":
				return UniformService.uploadLiveFavorites(jsonObject2);
			case "uploadVodFavorites":
				return UniformService.uploadVodFavorites(jsonObject2);
			case "getLiveFavorites":
				return UniformService.getLiveFavorites(jsonObject2);
			case "getVodFavorites":
				return UniformService.getVodFavorites(jsonObject2);
			case "deleteVodFavorites":
				return UniformService.deleteVodFavorites(jsonObject2);
			case "deleteVodPlayHistorys":
				return UniformService.deleteVodPlayHistorys(jsonObject2);
			case "deleteLiveFavorites":
				return UniformService.deleteLiveFavorites(jsonObject2);
			}
		} catch (Exception e) {
			LOGGER.error("统一接口访问异常=类名：UniformInterfaceController-方法名：common", e);
			json.put("code", 0);
			json.put("message", "接口访问出错！");
		}
		json.put("code", 0);
		json.put("message", "参数出错！");
		return JSON.toJSONString(json);
	}

	@RequestMapping("/cpUploadAlbum")
	@ResponseBody
	public String cpUploadAlbum(@RequestBody ImportAlbuminfo data) {
		HashMap<String, Object> json = new HashMap<>();
		if (data == null) {
			json.put("code", 0);
			json.put("message", "参数格式不正确！");
			return JSON.toJSONString(json);
		}
		String cpCode = data.getCpCode();
		List<CpAlbuminfo> list = data.getList();
		if (cpCode == null || "".equals(cpCode)) {
			json.put("code", 0);
			json.put("message", "cpCode不能为空！");
			return JSON.toJSONString(json);
		}
		try {
			cpAlbuminfoService.cpUploadAlbum(list, cpCode);
			json.put("code", 1);
			json.put("message", "同步成功！");
		} catch (Exception e) {
			LOGGER.error("统一接口访问异常=类名：UniformInterfaceController-方法名：common", e);
			json.put("code", 0);
			json.put("message", "接口访问出错！");
		}
		return JSON.toJSONString(json);
	}

	@RequestMapping("/cpUploadDrama")
	@ResponseBody
	public String cpUploadDrama(@RequestBody ImportDrama data) {
		HashMap<String, Object> json = new HashMap<>();
		if (data == null) {
			json.put("code", 0);
			json.put("message", "参数格式不正确！");
			return JSON.toJSONString(json);
		}
		String cpCode = data.getCpCode();
		List<CpDrama> list = data.getList();
		if (cpCode == null || "".equals(cpCode)) {
			json.put("code", 0);
			json.put("message", "cpCode不能为空！");
			return JSON.toJSONString(json);
		}
		try {
			cpDramaService.cpUploadDrama(list, cpCode);
			json.put("code", 1);
			json.put("message", "同步成功！");
		} catch (Exception e) {
			LOGGER.error("统一接口访问异常=类名：UniformInterfaceController-方法名：cpUploadDrama", e);
			json = new HashMap<>();
			json.put("code", 0);
			json.put("message", "接口访问出错！");
		}
		return JSON.toJSONString(json);
	}

	@RequestMapping("/cpUploadChannel")
	@ResponseBody
	public String cpUploadChannel(@RequestBody ImportChannel data) {
		HashMap<String, Object> json = new HashMap<>();
		if (data == null) {
			json.put("code", 0);
			json.put("message", "参数格式不正确！");
			return JSON.toJSONString(json);
		}
		String cpCode = data.getCpCode();
		List<CpChannel> list = data.getList();
		if (cpCode == null || "".equals(cpCode)) {
			json.put("code", 0);
			json.put("message", "cpCode不能为空！");
			return JSON.toJSONString(json);
		}
		try {
			cpChannelService.cpUploadChannel(list, cpCode);
			json.put("code", 1);
			json.put("message", "同步成功！");
		} catch (Exception e) {
			LOGGER.error("统一接口访问异常=类名：UniformInterfaceController-方法名：cpUploadDrama", e);
			json = new HashMap<>();
			json.put("code", 0);
			json.put("message", "接口访问出错！");
		}
		return JSON.toJSONString(json);
	}

	@RequestMapping("/statusSynchronization")
	@ResponseBody
	public String statusSynchronization(@RequestBody ImportAlbuminfoStatus data) {
		HashMap<String, Object> json = new HashMap<>();
		if (data == null) {
			json.put("code", 0);
			json.put("message", "参数格式不正确！");
			return JSON.toJSONString(json);
		}
		String cpCode = data.getCpCode();
		Long cpSeriesCode = data.getCpSeriesCode();
		Integer status = data.getStatus();
		if (cpCode == null || "".equals(cpCode)) {
			json.put("code", 0);
			json.put("message", "cpCode不能为空！");
			return JSON.toJSONString(json);
		}
		if (cpSeriesCode == null) {
			json.put("code", 0);
			json.put("message", "cpSeriesCode不能为空！");
			return JSON.toJSONString(json);
		}
		if (status == null) {
			json.put("code", 0);
			json.put("message", "status不能为空！");
			return JSON.toJSONString(json);
		}
		try {
			cpAlbuminfoService.updateStatus(cpCode, cpSeriesCode, status);
			json.put("code", 1);
			json.put("message", "同步成功！");
		} catch (Exception e) {
			LOGGER.error("cp方专辑同步状态访问异常=类名：UniformInterfaceController-方法名：stateSynchronization", e);
			json.put("code", 0);
			json.put("message", "接口访问出错！");
		}
		return JSON.toJSONString(json);
	}

	@RequestMapping("/uploadTvAlbum")
	@ResponseBody
	public String uploadTvAlbum(String data) {
		HashMap<String, Object> json = new HashMap<>();
		LOGGER.info(data);
		try {
			List<HashMap> list = JSON.parseArray(data, HashMap.class);
			if (list != null && list.size() > 0) {
				albuminfoService.insertList(list, AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_TV);
				json.put("code", 1);
				json.put("message", "上传成功！");
			} else {
				json.put("code", 0);
				json.put("message", "数据为空！");
				LOGGER.warn("uploadTvAlbum上传数据为空！");
			}
			json.put("code", 1);
			json.put("message", "上传成功！");
		} catch (Exception e) {
			LOGGER.error("专辑同步状态访问异常=类名：UniformInterfaceController-方法名：uploadTvAlbum", e);
			json.put("code", 0);
			json.put("message", "接口访问出错！");
		}
		return JSON.toJSONString(json);
	}

	@RequestMapping("/uploadPhoneAlbum")
	@ResponseBody
	public String uploadPhoneAlbum(String data) {
		HashMap<String, Object> json = new HashMap<>();
		LOGGER.info(data);
		try {
			List<HashMap> list = JSON.parseArray(data, HashMap.class);
			if (list != null && list.size() > 0) {
				albuminfoService.insertList(list, AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_PHONE);
				json.put("code", 1);
				json.put("message", "上传成功！");
			} else {
				json.put("code", 0);
				json.put("message", "数据为空！");
				LOGGER.warn("uploadPhoneAlbum上传数据为空！");
			}
		} catch (Exception e) {
			LOGGER.error("专辑同步状态访问异常=类名：UniformInterfaceController-方法名：uploadPhoneAlbum", e);
			json.put("code", 0);
			json.put("message", "接口访问出错！");
		}
		return JSON.toJSONString(json);
	}

	@RequestMapping("/uploadDrama")
	@ResponseBody
	public String uploadDrama(String data) {
		HashMap<String, Object> json = new HashMap<>();
		LOGGER.debug(data);
		try {
			List<HashMap> list = JSON.parseArray(data, HashMap.class);
			if (list != null && list.size() > 0) {
				dramaService.insertList(list);
				json.put("code", 1);
				json.put("message", "上传成功！");
			} else {
				json.put("code", 0);
				json.put("message", "数据为空！");
				LOGGER.warn("uploadDrama上传数据为空！");
			}
		} catch (Exception e) {
			LOGGER.error("剧集同步状态访问异常=类名：UniformInterfaceController-方法名：uploadDrama", e);
			json.put("code", 0);
			json.put("message", "接口访问出错！");
		}
		return JSON.toJSONString(json);
	}
}
