package com.pbtd.playuser.web.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbtd.playuser.util.UserVodConstant;

@RestController
@RequestMapping("/user")
public class InterceptorReturnController {
	@RequestMapping(value = "/tokenValidateReturn")
	public HashMap<String, Object> tokenValidateReturn() {
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("message", "token校验失败！");
		json.put("code", UserVodConstant.USER_NOT_LOG_IN);
		return json;
	}
}
