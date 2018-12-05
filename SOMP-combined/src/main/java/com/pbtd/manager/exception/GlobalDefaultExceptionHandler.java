package com.pbtd.manager.exception;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String defaultExceptionHandler(Exception exception) {
		logger.error("接口异常抛出！", exception);
		HashMap<String, Object> json = new HashMap<>();
		json.put("code", 0);
		json.put("message", "接口访问出错！");
		return JSON.toJSONString(json);
	}
}
