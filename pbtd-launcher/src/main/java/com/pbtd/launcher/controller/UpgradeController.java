package com.pbtd.launcher.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.launcher.conf.ConstantBeanConfig;
import com.pbtd.launcher.dto.UpgradeDTO;
import com.pbtd.launcher.exception.JsonMessageException;
import com.pbtd.launcher.service.UpgradeService;

/**
 * 无用，可删
 * @author jiaren
 *
 */
@Controller
@RequestMapping("/tv/launcher")
public class UpgradeController {
	private static final Logger logger = LoggerFactory.getLogger(UpgradeController.class);
	@Autowired
	private UpgradeService upgradeService;
	@Autowired
	private ConstantBeanConfig constant;

	@RequestMapping(value = "/validateUpgradeInfo")
	@ResponseBody
	public HashMap<String, Object> validateUpgradeInfo(HttpServletRequest request) {
		HashMap<String, Object> json = new HashMap<>();
		String proj_id = null;
		Integer type = null;
		String version = null;
		try {
			proj_id = request.getParameter("proj_id");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "proj_id参数类型错误！");
			json.put("data", "{}");
			return json;
		}
		try {
			type = Integer.valueOf(request.getParameter("type"));
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "type参数类型错误！");
			json.put("data", "{}");
			return json;
		}
		try {
			version = request.getParameter("version");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "version参数类型错误！");
			json.put("data", "{}");
			return json;
		}
		if (proj_id == null || type == null || version == null) {
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
			UpgradeDTO upgrade = upgradeService.queryValidateVersion(type, version);
			if (upgrade != null) {
				json.put("code", 1);
				json.put("message", "响应成功！");
				json.put("data", upgrade);
			} else {
				json.put("code", 0);
				json.put("message", "当前已是最新版本！");
				json.put("data", "{}");
			}
		} catch (JsonMessageException e) {
			json.put("code", 0);
			json.put("message", e.getMessage());
			json.put("data", "{}");
			logger.warn("launcher升级校验接口-方法名称：validateUpgradeInfo;", e);
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "数据查询出错！");
			json.put("data", "{}");
			logger.error("launcher升级校验接口-方法名称：validateUpgradeInfo;", e);
		}
		return json;
	}
}
