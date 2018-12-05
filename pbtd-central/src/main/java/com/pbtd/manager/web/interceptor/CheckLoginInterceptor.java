package com.pbtd.manager.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.manager.util.LoginInfoContext;

/**
 * 未登录用户访问需要登录用户才能访问页面的拦截器
 * 
 * @author sum
 *
 */
public class CheckLoginInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			LoginInfo currentInto = LoginInfoContext.getCurrent();
			if (currentInto == null) {
				// 跳转登录页面
				response.sendRedirect("/");
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 请求处理之后视图渲染之前进行调用
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 在视图渲染之后调用
	}
}
