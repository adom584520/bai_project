package com.pbtd.playuser.exception;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public HashMap<String, Object> defaultExceptionHandler(Exception exception) {
		logger.error("接口校验参数失败！", exception);
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("code", 0);
		json.put("message", "参数校验错误！");
		json.put("data", "{}");
		return json;
	}
}
