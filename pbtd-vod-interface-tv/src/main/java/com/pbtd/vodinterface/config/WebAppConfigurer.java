package com.pbtd.vodinterface.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pbtd.vodinterface.web.interceptor.LogInterceptor;

/**
 * web mvc配置配置
 * 
 * @author JOJO
 *
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
	@Bean
	public LogInterceptor logInterceptor() {
		return new LogInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
	//	registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
