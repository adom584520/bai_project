package com.pbtd.launcher.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbtd.launcher.annotation.LogAnnotation;
import com.pbtd.launcher.conf.ConstantBeanConfig;
import com.pbtd.launcher.dto.LauncherGroupDTO;
import com.pbtd.launcher.service.LauncherGroupService;

@RestController
@RequestMapping("/tv/launcher")
public class LauncherGroupController {
	private static final Logger logger = LoggerFactory.getLogger(LauncherGroupController.class);
	@Autowired
	private LauncherGroupService launcherGroupService;
	@Autowired
	private ConstantBeanConfig constant;

	@LogAnnotation(operationInfo = "launcher_获取所有分组和导航名称接口")
	@RequestMapping(value = "/getAllGroupAndNav", params = { "proj_id" })
	public HashMap<String, Object> getAllGroupIdAndNav(HttpServletRequest request) {
		HashMap<String, Object> json = new HashMap<>();
		String proj_id = null;
		try {
			proj_id = request.getParameter("proj_id");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "proj_id参数类型错误！");
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
			List<LauncherGroupDTO> data = launcherGroupService.queryAllGroupIdAndName();
			if (data != null && data.size() > 0) {
				json.put("code", 1);
				json.put("message", "响应成功！");
				json.put("data", data);
			} else {
				json.put("code", 0);
				json.put("message", "无数据！");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "数据查询出错！");
			json.put("data", "{}");
			logger.error("launcher_获取所有分组和导航接口-方法名称：getAllGroupIdAndNav;", e);
		}
		return json;
	}
}
