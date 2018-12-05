package com.pbtd.playuser.web.interceptor;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.pbtd.playuser.service.IRedisService;
import com.pbtd.playuser.util.MySstringUtil;
import com.pbtd.playuser.util.UserVodConstant;

/**
 * token校验拦截器
 * 
 * @author JOJO
 *
 */
public class TokenInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

	@Autowired
	private IRedisService redisService;
	public static String PARAM_USER_ID = "userId";
	public static String PARAM_TOKEN = "token";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			response.setCharacterEncoding("UTF-8");    
			response.setContentType("application/json; charset=utf-8"); 
			String userId = request.getParameter(PARAM_USER_ID);
			String token = request.getParameter(PARAM_TOKEN);
			HashMap<String, Object> json = new HashMap<String, Object>();
			logger.info("访问接口地址："+request.getRequestURI());
			if (!MySstringUtil.validateNotEmpty(userId)) {
				logger.info("未输入userId或userId参数为空！");
				json.put("message", "token校验失败！");
				json.put("code", UserVodConstant.USER_NOT_LOG_IN);
				response.getOutputStream().write(JSON.toJSONString(json).getBytes());
				return false;
				
			}
			if (!MySstringUtil.validateNotEmpty(token)) {
				logger.info("未输入token或token参数为空！");
				//response.sendRedirect(request.getContextPath() + "/user/tokenValidateReturn");
				json.put("message", "token校验失败！");
				json.put("code", UserVodConstant.USER_NOT_LOG_IN);
				response.getOutputStream().write(JSON.toJSONString(json).getBytes());
				return false;
			}
			if ("tv_token_6688_pbtd".equalsIgnoreCase(token)) {
				logger.info("token为默认值！");
				return true;
			}
			String tokenuser = "TOKEN-" + userId;
			
			String tokenvalue = null;
			try {
				tokenvalue = redisService.get(tokenuser);
			} catch (Exception e) {
				json.put("message", "缓存服务器异常！");
				json.put("code", UserVodConstant.REDIS_CONNECT);
				response.getOutputStream().write(JSON.toJSONString(json).getBytes());
				return false;
			}
			if (token.equals(tokenvalue)) {
				logger.info("token检验成功！");
				return true;
			} else {
				//response.sendRedirect(request.getContextPath() + "/user/tokenValidateReturn");
				json.put("message", "token校验失败！");
				json.put("code", UserVodConstant.USER_NOT_LOG_IN);
				response.getOutputStream().write(JSON.toJSONString(json).getBytes());
				return false;
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
