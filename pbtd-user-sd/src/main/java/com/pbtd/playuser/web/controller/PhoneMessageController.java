package com.pbtd.playuser.web.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pbtd.playuser.domain.PhoneMessage;
import com.pbtd.playuser.service.PhoneMessageService;

@Controller
public class PhoneMessageController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PhoneMessageController.class);
	@Autowired
	private PhoneMessageService phoneMessageService;

	@RequestMapping(value = "/phoneMessage/page")
	public String page() {
		return "/phoneMessage";
	}

	@RequestMapping(value = "/phoneMessage/updateIOSAudit", params = { "status" })
	@ResponseBody
	public String updateIOSAudit(Integer status) {
		HashMap<String, Object> json = new HashMap<>();
		try {
			phoneMessageService.updateIOSAudit(status);
			json.put("message", "修改成功！");
			json.put("code", 1);
		} catch (Exception e) {
			json.put("message", "系统错误!");
			json.put("code", 0);
			LOGGER.error("iOS审核状态修改接口出错！类名：PhoneMessageController-方法名：updateIOSAudit", e);
		}
		return JSON.toJSONString(json);
	}

	@RequestMapping(value = "/phoneMessageAction!phoneAudit.action")
	@ResponseBody
	public String iOSAudit(Integer status) {
		HashMap<String, Object> json = new HashMap<>();
		try {
			PhoneMessage iOSAudit = phoneMessageService.iOSAudit(status);
			if (PhoneMessage.STATUS_IN_AUDIT.equals(iOSAudit.getStatus())) {
				json.put("isOnCheckTime", true);
			} else {
				json.put("isOnCheckTime", true);
			}
		} catch (Exception e) {
			json.put("isOnCheckTime", false);
			LOGGER.error("iOS审核状态查询接口出错！类名：PhoneMessageController-方法名：iOSAudit", e);
		}
		return JSON.toJSONString(json);
	}
}
