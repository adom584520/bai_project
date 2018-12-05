package com.pbtd.playuser.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbtd.playuser.domain.PositionPoint;
import com.pbtd.playuser.service.PositionPointService;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/phone/user")
public class PositionPointController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PositionPointController.class);
	@Autowired
	private PositionPointService positionPointService;

	@RequestMapping(value = "/insertPositionPoint", params = { "userId", "pos", "type" })
	public String insertPositionPoint(PositionPoint pp) {
		LOGGER.debug("用户系统-运营位添加打点记录接口-添加失败！");
		JSONObject json = new JSONObject();
		try {
			positionPointService.insert(pp);
			json.put("message", "添加成功！");
			json.put("code", 1);
			json.put("data", "{}");
		} catch (Exception e) {
			json.put("message", "添加失败！");
			json.put("code", 0);
			json.put("data", "{}");
			LOGGER.error("用户系统-运营位添加打点记录接口-添加失败！", e);
		}
		return json.toString();
	}
}
