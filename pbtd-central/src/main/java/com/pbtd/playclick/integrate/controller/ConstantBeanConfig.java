package com.pbtd.playclick.integrate.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Configuration
@PropertySource(value = { "classpath:config/constant.properties" })
public class ConstantBeanConfig {
	@Value("${my_upload_image_url}")
	public String uploadImageUrl;	//图片上传路径
	@Value("${my_upload_image_look_url}")
	public String uploadImageLookUrl;//图片访问路径
	@Value("${my_upload_image_web_path}")
	public String uploadImageWebPath;//图片上传web根路径临时目录
}
