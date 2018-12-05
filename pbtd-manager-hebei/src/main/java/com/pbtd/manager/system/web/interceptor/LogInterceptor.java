package com.pbtd.manager.system.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pbtd.manager.system.domain.SystemLogger;
import com.pbtd.manager.system.service.SystemLoggerService;
import com.pbtd.manager.system.util.LoggerUtil;
import com.pbtd.manager.system.util.LoginInfoContext;

/**
 * 后台用户操作记录的拦截器（日志改为用aop实现，这个暂时不注册和不使用）
 * 
 * @author JOJO
 *
 */
public class LogInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	@Autowired
	private SystemLoggerService systemLoggerService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 只有映射到方法上才进行登录判断
		if (handler instanceof HandlerMethod) {
			// 创建日志实体
			SystemLogger sysLogger = new SystemLogger();
			// 请求路径
			String url = request.getRequestURI();
			// 获取请求参数信息
			String paramData = JSON.toJSONString(request.getParameterMap(),
					SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
			// 设置客户端ip
			sysLogger.setClientIp(LoggerUtil.getCliectIp(request));
			// 设置请求方法
			sysLogger.setMethod(request.getMethod());
			// 设置请求类型（json|普通请求）
			sysLogger.setType(LoggerUtil.getRequestType(request));
			// 设置请求参数内容json字符串
			sysLogger.setParamData(paramData);
			// 设置请求地址
			sysLogger.setUrl(url);
			// 设置请求开始时间
			sysLogger.setStartTime(Long.toString(System.currentTimeMillis()));
			// 设置请求实体到request内，方面afterCompletion方法调用
			request.setAttribute(LoggerUtil.LOGININFO_LOGGER, sysLogger);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 只有映射到方法上才进行登录判断
		if (handler instanceof HandlerMethod) {
			// 当前时间
			long currentTime = System.currentTimeMillis();
			// 获取本次请求日志实体
			SystemLogger sysLogger = (SystemLogger) request.getAttribute(LoggerUtil.LOGININFO_LOGGER);
			// 请求开始时间
			long time = Long.valueOf(sysLogger.getStartTime());
			// 设置返回时间
			sysLogger.setReturnTime(Long.toString(currentTime));
			// 设置请求时间差
			sysLogger.setConsumingTime(Integer.valueOf(Long.toString(currentTime - time)));
			// 设置操作信息
			sysLogger.setOperationInfo((String) (request.getAttribute(LoggerUtil.LOGGER_OPERATION_INFO)));
			// 设置返回错误码
			sysLogger.setHttpStatusCode(Integer.toString(response.getStatus()));
			// 设置用户账号名称
			sysLogger.setLoginInfoName(LoginInfoContext.getCurrent().getUsername());
			// 设置响应参数
			sysLogger.setReturnData(JSON.toJSONString(request.getAttribute(LoggerUtil.LOGGER_RETURN_DATA),
					SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue));
			// 执行将日志写入数据库
			try {
				systemLoggerService.insert(sysLogger);
			} catch (Exception e) {
				logger.error("系统管理-日志模块-日志保存出错", e);
			}
		}
	}
}
