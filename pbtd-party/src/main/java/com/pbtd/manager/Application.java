package com.pbtd.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 启动类
 * 
 * @author JOJO
 */

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		// 禁止命令行设置参数
		springApplication.setAddCommandLineProperties(false);
		SpringApplication.run(Application.class, args);
	}
}
