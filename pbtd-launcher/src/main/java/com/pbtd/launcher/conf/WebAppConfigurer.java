package com.pbtd.launcher.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.pbtd.launcher.interceptor.LogInterceptor;

/**
 * web mvc配置配置
 * 
 * @author JOJO
 *
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
	// 创建fastjson转换器
	@Bean
	public FastJsonHttpMessageConverterEx fastJsonHttpMessageConverterEx() {
		return new FastJsonHttpMessageConverterEx();
	}

	// 添加fastjson转换器
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverterEx fastJsonHttpMessageConverterEx = fastJsonHttpMessageConverterEx();
		// 设置fastjson解析器的解析编码
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastJsonHttpMessageConverterEx.setSupportedMediaTypes(fastMediaTypes);
		// fastjson解析器的特性功能设置
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures( // 美化响应的json字符串
				SerializerFeature.WriteNullNumberAsZero, // 数字为NULL时输出0
				SerializerFeature.WriteNullListAsEmpty, // 集合为NULL时输出[]
				SerializerFeature.WriteNullStringAsEmpty, // 字符串为NULL时输出""
				SerializerFeature.WriteMapNullValue);
		// 自定义时间格式
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonHttpMessageConverterEx.setFastJsonConfig(fastJsonConfig);
		// 将fastjson转换器加入HttpMessageConverter集合中
		converters.add(fastJsonHttpMessageConverterEx);
		super.configureMessageConverters(converters);
	}

	@Bean
	public LogInterceptor logInterceptor() {
		return new LogInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
