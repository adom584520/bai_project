package com.pbtd.playlive.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class LoggerUtil {
	public static final String LOGGER_RETURN_DATA = "loggerReturnData";
	public static final String LOGGER_START_TIME = "loggerStartTime";
	public static final String LOGININFO_LOGGER = "LoginInfoLogger";
	public static final String LOGGER_OPERATION_INFO = "loggerOperationInfo";

	/**
	 * 获取客户端的IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getCliectIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ip.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ip = str;
				break;
			}
		}
		return ip;
	}

	/**
	 * 判断是否为ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestType(HttpServletRequest request) {
		return request.getHeader("X-Requested-With");
	}

	/**
	 * 设置操作信息和返回参数
	 * 
	 * @param request
	 */
	public static void setInfoAndReturnData(String operationInfo, Object obj) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		request.setAttribute(LOGGER_OPERATION_INFO, operationInfo);
		request.setAttribute(LOGGER_RETURN_DATA, obj);
	}
}
