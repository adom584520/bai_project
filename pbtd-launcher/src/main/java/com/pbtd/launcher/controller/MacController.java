package com.pbtd.launcher.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbtd.launcher.annotation.LogAnnotation;
import com.pbtd.launcher.conf.ConstantBeanConfig;
import com.pbtd.launcher.domain.Mac;
import com.pbtd.launcher.dto.MacDTO;
import com.pbtd.launcher.service.MacService;

@RestController
@RequestMapping("/tv/launcher")
public class MacController {
	private static final Logger logger = LoggerFactory.getLogger(MacController.class);
	@Autowired
	private MacService macService;
	@Autowired
	private ConstantBeanConfig constant;

	@RequestMapping(value = "/getGroupByMac", params = { "proj_id", "mac" })
	@LogAnnotation(operationInfo = "launcher_根据MAC获取分组ID接口")
	public HashMap<String, Object> getGroupByMac(HttpServletRequest request) {
		HashMap<String, Object> json = new HashMap<>();
		String proj_id = null;
		String mac = null;
		try {
			proj_id = request.getParameter("proj_id");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "proj_id参数类型错误！");
			json.put("data", "{}");
			return json;
		}
		try {
			mac = (String) request.getParameter("mac");
			if (!(mac != null && mac.length() > 0)) {
				mac = null;
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "mac参数类型错误！");
			json.put("data", "{}");
			return json;
		}
		if (proj_id == null || mac == null) {
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
			Mac newMac = macService.queryByMac(mac);
			if (newMac != null) {
				MacDTO macDTO = new MacDTO(newMac.getId(), newMac.getMacName(), newMac.getGroupId());
				json.put("code", 1);
				json.put("message", "响应成功！");
				json.put("data", macDTO);
			} else {
				json.put("code", 0);
				json.put("message", "无数据！");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "数据查询出错！");
			json.put("data", "{}");
			logger.error("launcher获取分组ID-方法名称：getGroupByMac;", e);
		}
		return json;
	}

}
