package com.pbtd.manager.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pbtd.manager.system.web.interceptor.CheckLoginInterceptor;
import com.pbtd.manager.system.web.interceptor.LogInterceptor;

/**
 * 拦截器配置
 * 
 * @author JOJO
 *
 */
@Configuration
public class InterceptorConfigurer extends WebMvcConfigurerAdapter {
	@Bean
	public LogInterceptor logInterceptor() {
		return new LogInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(new CheckLoginInterceptor()).excludePathPatterns("/login", "/",
				"/integrate/outputjson/*/*", "/live/outputjson/*/*", "/live/outputjson/*",
				"/tv/vod/albuminfo/validateSeriesCode/**","/tv/vod/module/**","/vod/tv/vodmouldinfo/**");
		super.addInterceptors(registry);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "DELETE", "PUT");
	}
}
