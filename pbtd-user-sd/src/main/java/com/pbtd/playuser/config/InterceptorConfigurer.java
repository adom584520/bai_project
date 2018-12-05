package com.pbtd.playuser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pbtd.playuser.web.interceptor.LogInterceptor;
import com.pbtd.playuser.web.interceptor.TokenInterceptor;

/**
 * 拦截器配置
 * 
 * @author JOJO
 *
 */
@Configuration
public class InterceptorConfigurer extends WebMvcConfigurerAdapter {
	@Bean
	public TokenInterceptor tokenInterceptor() {
		return new TokenInterceptor();
	}

	@Bean
	public LogInterceptor logInterceptor() {
		return new LogInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**").excludePathPatterns(
				"/user/tokenValidateReturn", "/common/user/sendCode*", "/common/user/login*", "/phone/user/liveCollect",
				"/tv/**", "/common/user/validateUpgradeInfo", "/userAgreement","/phoneMessage**/**");
		// registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").
		allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE",
				"OPTIONS", "TRACE");
	}
}
