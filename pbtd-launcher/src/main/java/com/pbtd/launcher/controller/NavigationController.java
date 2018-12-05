package com.pbtd.launcher.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.pbtd.launcher.annotation.LogAnnotation;
import com.pbtd.launcher.conf.ConstantBeanConfig;
import com.pbtd.launcher.dto.NavigationDTO;
import com.pbtd.launcher.service.NavigationService;

@RestController
@RequestMapping("/tv/launcher")
public class NavigationController {
	private static final Logger logger = LoggerFactory.getLogger(NavigationController.class);
	@Autowired
	private NavigationService navigationService;
	@Autowired
	private ConstantBeanConfig constant;

	@RequestMapping(value = "/getNavigationByGroupId", params = { "proj_id", "groupId" })
	@LogAnnotation(operationInfo = "launcher_根据分组ID获取导航列表接口")
	public HashMap<String, Object> getNavigation(HttpServletRequest request) {
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
			List<NavigationDTO> data = navigationService.queryNavListByGroupId(groupId);
			if (data != null && data.size() > 0) {
				for (int i = 0; i < data.size(); i++) {
					NavigationDTO nav = data.get(i);
					if (nav.getParamKey() != null && nav.getParamKey().length() > 0) {
						nav.setParamKeys(JSON.parseArray(nav.getParamKey(), String.class));
					}
					if (nav.getParamValue() != null && nav.getParamValue().length() > 0) {
						nav.setParamValues(JSON.parseArray(nav.getParamValue(), String.class));
					}
				}
			}
			if (data != null) {
				json.put("code", 1);
				json.put("message", "响应成功！");
				json.put("data", data);
			} else {
				json.put("code", 0);
				json.put("message", "无数据！");
				json.put("data", "[]");
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "数据查询出错！");
			json.put("data", "[]");
			logger.error("launcher获取导航-方法名称：getNavigation;", e);
		}
		return json;
	}
}
