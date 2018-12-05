package com.pbtd.manager.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.util.ResultBean;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResultBean<Object> defaultExceptionHandler(Exception exception) {
		ResultBean<Object> json = new ResultBean<>();
		json.setSuccess(false);
		json.setMessage("服务器繁忙，请稍后再试！");
		logger.error("未知错误", exception);
		return json;
	}
}
