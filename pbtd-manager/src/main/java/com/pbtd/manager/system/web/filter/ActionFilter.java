package com.pbtd.manager.system.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pbtd.manager.system.util.LoginInfoContext;

public class ActionFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LoginInfoContext.setRequest((HttpServletRequest) request);
		LoginInfoContext.setResponse((HttpServletResponse) response);
		chain.doFilter(request, response);
	}

	public void destroy() {

	}

}
