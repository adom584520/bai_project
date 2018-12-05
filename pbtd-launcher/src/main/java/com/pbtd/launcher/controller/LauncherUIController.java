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
import com.pbtd.launcher.domain.LauncherUI;
import com.pbtd.launcher.dto.LauncherUIDTO;
import com.pbtd.launcher.service.LauncherUIService;

@RestController
@RequestMapping("/tv/launcher")
public class LauncherUIController {
	private static final Logger logger = LoggerFactory.getLogger(LauncherUIController.class);
	@Autowired
	private LauncherUIService launcherUIService;
	@Autowired
	private ConstantBeanConfig constant;

	@RequestMapping(value = "/getUiByGroupId", params = { "proj_id", "groupId" })
	@LogAnnotation(operationInfo = "launcher_界面UI接口")
	public HashMap<String, Object> getUI(HttpServletRequest request) {
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
			LauncherUI ui = launcherUIService.queryByGroupId(groupId);
			if (ui != null) {
				LauncherUIDTO uiDTO = new LauncherUIDTO();
				uiDTO.setGroupId(ui.getGroupId());
				uiDTO.setId(ui.getId());
				uiDTO.setLogo(ui.getLogo());
				uiDTO.setPickerView(ui.getPickerView());
				uiDTO.setPosterBackground(ui.getPosterBackground());
				uiDTO.setWatchBackground(ui.getWatchBackground());
				json.put("code", 1);
				json.put("message", "响应成功！");
				json.put("data", uiDTO);
			} else {
				json.put("code", 0);
				json.put("message", "无数据！");
				json.put("data", "{}");
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "数据查询出错！");
			json.put("data", "{}");
			logger.error("launcher获取logo和背景接口-方法名称：getUI;", e);
		}
		return json;
	}
}
