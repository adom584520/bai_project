package com.pbtd.manager.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
	
	@Bean
	public CheckLoginInterceptor checkLoginInterceptor() {
		return new CheckLoginInterceptor();
	}
	
	
	public void addInterceptors(InterceptorRegistry registry) {
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(new CheckLoginInterceptor()).addPathPatterns("/**")
		.excludePathPatterns("/","/login","/integrate/outputjson/*","/jsonYouku/somp_getvideo/*","/jsonYouku/somp_getalbum/*");
		super.addInterceptors(registry);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").
		allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE",
				"OPTIONS", "TRACE");
	}
}
