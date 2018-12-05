package com.pbtd.manager.conf;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pbtd.manager.system.web.filter.ActionFilter;

/**
 * 过滤器配置
 * 
 * @author JOJO
 *
 */
@Configuration
public class FilterConfiguration {
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new ActionFilter());
		registrationBean.addInitParameter("exclusions", "*.jsp,*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
//		registrationBean.addInitParameter("urlPatterns", "/**");
		return registrationBean;
	}
}
