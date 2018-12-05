package com.pbtd.manager.conf;

import java.util.ArrayList;
import java.util.List;

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
		ActionFilter actionFilter = new ActionFilter();
		registrationBean.setFilter(actionFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		urlPatterns.add("/**");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
}
