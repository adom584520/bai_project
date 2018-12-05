/**
 * @author liuxiaomeng
 * @datetime 2015-8-12_上午10:44:09
 */
package com.zckj.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * session过期过滤
 * 
 * @author liuxiaomeng
 * @datetime 2015-8-12_上午10:44:09
 */
public class SessionFilter implements Filter {
	@Override
	public void destroy() { // TODO Auto-generated method stub     
	}

	/**
	 * session过期验证，重新指向页面
	 * 
	 * @author liuxiaomeng
	 * @datetime 2015-8-12_上午10:48:28
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		//final HttpServletResponse httpResponse = (HttpServletResponse) response;
		final HttpSession session = httpRequest.getSession(); // 登陆url        
		final String loginUrl = httpRequest.getContextPath() + "/version.jsp";
		final String url = httpRequest.getRequestURI();
		final String path = url.substring(url.lastIndexOf("/")); // 超时处理，ajax请求超时设置超时状态，页面请求超时则返回提示并重定向        
		if ((path.indexOf("Auction") != -1) && (session.getAttribute("LOGIN_SUCCESS") == null)) { // 判断是否为ajax请求         
			//if ((httpRequest.getHeader("x-requested-with") != null) && httpRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				//httpResponse.addHeader("sessionstatus", "timeOut");
				//httpResponse.addHeader("loginPath", loginUrl);
				//chain.doFilter(request, response);// 不可少，否则请求会出错            
			//} else {
				final String str = "<script language='javascript'>alert('会话过期,请刷新页面后操作！');" + "window.top.location.href='" + loginUrl + "';</script>";
				response.setContentType("text/html;charset=UTF-8");// 解决中文乱码           
				try {
					final PrintWriter writer = response.getWriter();
					writer.write(str);
					writer.flush();
					writer.close();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			//}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(final FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub    
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-12_上午10:44:09
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO 需要完善相关逻辑
	}
}
