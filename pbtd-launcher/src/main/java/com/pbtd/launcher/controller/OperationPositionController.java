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
import com.pbtd.launcher.dto.OperationPositionDTO;
import com.pbtd.launcher.service.OperationPositionService;

@RestController
@RequestMapping("/tv/launcher")
public class OperationPositionController {
	private static final Logger logger = LoggerFactory.getLogger(LauncherUIController.class);
	@Autowired
	private OperationPositionService operationPositionService;
	@Autowired
	private ConstantBeanConfig constant;

	@RequestMapping(value = "/getOperationPositionListByNavId", params = { "proj_id", "groupId", "navId" })
	@LogAnnotation(operationInfo = "launcher_根据导航ID获取运营位列表")
	public HashMap<String, Object> getOperationPosition(HttpServletRequest request) {
		HashMap<String, Object> json = new HashMap<>();
		String proj_id = null;
		Long groupId = null;
		Long navId = null;
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
		try {
			navId = Long.valueOf(request.getParameter("navId"));
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "navId参数类型错误！");
			json.put("data", "{}");
			return json;
		}
		if (proj_id == null || groupId == null || navId == null) {
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
			List<OperationPositionDTO> data = operationPositionService.queryOpListByNavId(navId);
			if (data != null&&data.size()>0) {
				for (int i = 0; i < data.size(); i++) {
					OperationPositionDTO op = data.get(i);
					if (op.getImages() != null && op.getImages().length() > 0) {
						op.setImageList(JSON.parseArray(op.getImages(), String.class));
					}
					if (op.getParamKey() != null && op.getParamKey().length() > 0) {
						op.setParamKeys(JSON.parseArray(op.getParamKey(), String.class));
					}
					if (op.getParamValue() != null && op.getParamValue().length() > 0) {
						op.setParamValues(JSON.parseArray(op.getParamValue(), String.class));
					}
				}
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
			logger.error("launcher获取运营位接口-方法名称：getOperationPosition;", e);
		}
		return json;
	}

}
