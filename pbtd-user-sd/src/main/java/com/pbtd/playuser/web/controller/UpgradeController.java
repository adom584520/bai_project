package com.pbtd.playuser.web.controller;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbtd.playuser.dto.UpgradeDTO;
import com.pbtd.playuser.service.UpgradeService;
import com.pbtd.playuser.util.JsonDateValueProcessor;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@RestController
@RequestMapping("/common/user")
public class UpgradeController {
	private static final Logger logger = LoggerFactory.getLogger(UpgradeController.class);
	@Autowired
	private UpgradeService upgradeService;

	@RequestMapping(value = "/validateUpgradeInfo", params = { "type", "version" })
	public String validateUpgradeInfo(Integer type, String version) {
		HashMap<String, Object> json = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject jsonObject = new JSONObject();
		try {
			UpgradeDTO data = upgradeService.queryValidateVersion(type, version);
			if (data != null) {
				if (data.getVersion().equals(version)) {
					json.put("message", "已是最新版本！");
					json.put("code", 0);
					json.put("data", "{}");
				} else {
					json.put("message", "响应成功！");
					json.put("code", 1);
					json.put("data", data);
				}
			} else {
				json.put("message", "暂无升级信息！");
				json.put("code", 0);
				json.put("data", "{}");
			}
		} catch (Exception e) {
			json.put("message", "查询失败！");
			json.put("code", 0);
			json.put("data", "{}");
			logger.error("用户系统-升级校验接口-查询升级信息失败！", e);
		}
		jsonObject.putAll(json, jsonConfig);
		return jsonObject.toString();
	}
}
