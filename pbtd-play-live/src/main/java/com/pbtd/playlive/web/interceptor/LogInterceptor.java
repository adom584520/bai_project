package com.pbtd.playlive.web.interceptor;

import java.util.Date;

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
import com.pbtd.playlive.annotation.LogAnnotation;
import com.pbtd.playlive.domain.Log;
import com.pbtd.playlive.service.LogService;
import com.pbtd.playlive.util.LoggerUtil;

/**
 * 接口日志拦截器
 * 
 * @author JOJO
 *
 */
public class LogInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	@Autowired
	private LogService logService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 只有映射到方法上才进行登录判断
		if (handler instanceof HandlerMethod) {
			LogAnnotation annotation = ((HandlerMethod) handler).getMethodAnnotation(LogAnnotation.class);
			if (annotation == null) {
				return false;
			}
			String operationInfo = annotation.operationInfo();
			// 创建日志实体
			Log log = new Log();
			// 设置操作信息
			log.setOperationInfo(operationInfo);
			// 请求路径
			String url = request.getRequestURI();
			// 获取请求参数信息
			String paramData = JSON.toJSONString(request.getParameterMap(),
					SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
			if (paramData != null && paramData.length() > 1020) {
				log.setParamData("请求参数信息过多，不予显示！");
			}
			// 设置客户端ip
			log.setClientIp(LoggerUtil.getCliectIp(request));
			// 设置请求方法
			log.setMethod(request.getMethod());
			// 设置请求类型（json|普通请求）
			log.setType(LoggerUtil.getRequestType(request));
			// 设置请求参数内容json字符串
			log.setParamData(paramData);
			// 设置请求地址
			log.setUrl(url);
			// 设置请求开始时间
			log.setStartTime(System.currentTimeMillis());
			// 设置请求实体到request内，方面afterCompletion方法调用
			request.setAttribute(LoggerUtil.LOGININFO_LOGGER, log);
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
		// 只有映射到方法上才拦截
		if (handler instanceof HandlerMethod) {
			// 当前时间
			long currentTime = System.currentTimeMillis();
			// 获取本次请求日志实体
			final Log log = (Log) request.getAttribute(LoggerUtil.LOGININFO_LOGGER);
			// 请求开始时间
			long time = Long.valueOf(log.getStartTime());
			// 设置返回时间
			log.setReturnTime(currentTime);
			// 设置请求时间差
			log.setConsumingTime(Integer.valueOf(Long.toString(currentTime - time)));
			// 设置返回错误码
			log.setHttpStatusCode(Integer.toString(response.getStatus()));
			log.setCreateTime(new Date());
			// 设置响应参数
			log.setReturnData("");
			new Thread() {
				public void run() {
					// 执行将日志写入数据库
					try {
						logService.insert(log);
					} catch (Exception e) {
						logger.error("luancher接口-日志保存出错", e);
					}
				}
			}.start();
		}
	}
}
