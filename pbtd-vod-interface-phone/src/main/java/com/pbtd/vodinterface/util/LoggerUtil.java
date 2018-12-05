package com.pbtd.vodinterface.util;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ch.qos.logback.classic.Logger;

public class LoggerUtil {
	public static final String LOGGER_RETURN_DATA = "loggerReturnData";
	public static final String LOGGER_START_TIME = "loggerStartTime";
	public static final String LOGININFO_LOGGER = "LoginInfoLogger";
	public static final String LOGGER_OPERATION_INFO = "loggerOperationInfo";

	private static final Logger logger = (Logger) LoggerFactory.getLogger(LoggerUtil.class);
	
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
	
	
	public static long logPreMethod(HttpServletRequest request){
		long beginTime=Calendar.getInstance().getTimeInMillis();
		StringBuilder builder=new StringBuilder();
		builder.append(" -----------------begin-------------------------");
		builder.append("\n请求路径：");
		String uri=request.getRequestURI();
		builder.append(uri);
		builder.append("  \n请求参数：");
		@SuppressWarnings("rawtypes")
		Enumeration em = request.getParameterNames();
		 while (em.hasMoreElements()) {
		    String name = (String) em.nextElement();
		    String value = request.getParameter(name);
		    builder.append(name+":"+value+" ");
		}
		logger.info(builder.toString());
		return beginTime;
	}
	
	
	public static void logReturnMethod(HttpServletRequest request,Object object,long beginTime){
		StringBuilder builder=new StringBuilder("\n请求路径：");
		String uri=request.getRequestURI();
		builder.append(uri);
		builder.append("  \n返回数据：");
		builder.append(object);
		long endTime=Calendar.getInstance().getTimeInMillis();
		long executeTime=endTime-beginTime;
		builder.append("  \n方法执行耗时："+executeTime+"ms");
		builder.append(" \n-------------------end-----------------------\n");
		logger.info(builder.toString());
	}
	
	
	
	
	
	
	
	
	
	
	
}
