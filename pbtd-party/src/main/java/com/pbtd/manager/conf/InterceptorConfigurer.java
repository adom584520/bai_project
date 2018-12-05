package com.pbtd.manager.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pbtd.manager.web.interceptor.CheckLoginInterceptor;

/**
 * 拦截器配置
 * 
 * @author JOJO
 *
 */
@Configuration
public class InterceptorConfigurer extends WebMvcConfigurerAdapter {
	/*
	 * @Bean public LogInterceptor logInterceptor() { return new
	 * LogInterceptor(); }
	 */

	@Bean
	public CheckLoginInterceptor checkLoginInterceptor() {
		return new CheckLoginInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(checkLoginInterceptor()).excludePathPatterns("/login", "/", "/tv/party/**");
		super.addInterceptors(registry);
	}
}
