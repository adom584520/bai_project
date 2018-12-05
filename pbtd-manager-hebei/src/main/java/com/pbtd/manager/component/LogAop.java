package com.pbtd.manager.component;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.domain.SystemLogger;
import com.pbtd.manager.system.service.SystemLoggerService;
import com.pbtd.manager.system.util.LoggerUtil;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.LogAnnotation;

@Aspect
@Component
public class LogAop {
	private static final Logger logger = LoggerFactory.getLogger(LogAop.class);

	@Autowired
	private SystemLoggerService systemLoggerService;

	@Pointcut("@annotation(com.pbtd.manager.util.LogAnnotation)")
	private void pointCutMethod() {
	}

	@Before("pointCutMethod()")
	public void before(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		// 创建日志实体
		SystemLogger sysLogger = new SystemLogger();
		// 请求路径
		String url = request.getRequestURI();
		// 设置客户端ip
		sysLogger.setClientIp(LoggerUtil.getCliectIp(request));
		// 设置请求方法
		sysLogger.setMethod(request.getMethod());
		// 设置请求类型（json|普通请求）
		sysLogger.setType(LoggerUtil.getRequestType(request));
		// 获取请求参数信息
		String paramData = JSON.toJSONString(request.getParameterMap(),
				SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
		if (paramData != null && paramData.length() > 1020) {
			sysLogger.setParamData("请求参数信息过多，不予显示！");
		}else{
			// 设置请求参数内容json字符串
			sysLogger.setParamData(paramData);
		}
		// 设置请求地址
		sysLogger.setUrl(url);
		// 设置请求开始时间
		sysLogger.setStartTime(System.currentTimeMillis() + "");
		// 设置请求实体到request内，方面afterCompletion方法调用
		request.setAttribute(LoggerUtil.LOGININFO_LOGGER, sysLogger);
	}

	/**
	 * 记录操作日志
	 */
	@AfterReturning(returning = "ret", pointcut = "pointCutMethod()") // 使用上面定义的切入点
	public void after(JoinPoint joinPoint, Object ret) {
		// 当前时间
		long currentTime = System.currentTimeMillis();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		// 获取本次请求日志实体
		SystemLogger sysLogger = (SystemLogger) request.getAttribute(LoggerUtil.LOGININFO_LOGGER);
		LoginInfo loginInfo = LoginInfoContext.getCurrent();
		if (loginInfo == null) {
			Object username = request.getAttribute("username");
			if (username == null) {
				logger.warn("系统管理-日志aop-账号未登录！");
				sysLogger.setLoginInfoName("未知用户");
			} else {
				sysLogger.setLoginInfoName((String) username);
			}
		} else {
			sysLogger.setLoginInfoName(loginInfo.getUsername());
		}
		// 请求开始时间
		long time = Long.valueOf(sysLogger.getStartTime());
		// 设置返回时间
		sysLogger.setReturnTime(currentTime + "");
		// 设置请求时间差
		sysLogger.setConsumingTime(Integer.valueOf(currentTime - time + ""));
		// 设置操作信息
		sysLogger.setOperationInfo(getOperationInfo(joinPoint));
		// 设置返回错误码
		sysLogger.setHttpStatusCode(LoginInfoContext.getResponse().getStatus() + "");
		// 设置响应参数
		String returnDate = JSON.toJSONString(ret, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteMapNullValue);
		// 执行将日志写入数据库
		try {
			if (returnDate != null && returnDate.length() > 1020) {
				sysLogger.setReturnData("返回信息过多，不予显示！");
			}else{
				sysLogger.setReturnData(returnDate);
			}
			systemLoggerService.insert(sysLogger);
		} catch (Exception e) {
			logger.error("系统管理-日志aop-日志保存出错", e);
		}
	}

	private String getOperationInfo(JoinPoint joinPoint) {
		String operationInfo = null;
		MethodSignature methodSignature = null;
		Method method = null;
		try {
			// 获取方法签名对象
			methodSignature = (MethodSignature) joinPoint.getSignature();
			// 获取方法对象
			method = methodSignature.getMethod();
			// 获取方法上的注解的值
			operationInfo = method.getAnnotation(LogAnnotation.class).operationInfo();
		} catch (Exception e) {
			logger.error("系统管理-日志aop-反射失败！", e);
		}
		return operationInfo;
	}
}
