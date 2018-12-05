package com.pbtd.manager.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playclick.util.JsonMessage;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JsonMessage defaultExceptionHandler(Exception exception) {
		JsonMessage json = new JsonMessage();
		json.setMessage("服务器繁忙，请稍后再试！");
		return json;
	}
}
