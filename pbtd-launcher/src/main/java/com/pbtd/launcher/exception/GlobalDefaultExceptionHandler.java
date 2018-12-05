package com.pbtd.launcher.exception;

import java.util.HashMap;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public HashMap<String, Object> defaultExceptionHandler(Exception exception) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("code", 0);
		json.put("message", "参数输入错误!");
		json.put("data", "{}");
		return json;
	}
}
