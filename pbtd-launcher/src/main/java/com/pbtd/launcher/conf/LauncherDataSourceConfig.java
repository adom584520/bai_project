package com.pbtd.launcher.conf;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * 数据库配置
 * 
 * @author JOJO
 *
 */
@Configuration
@MapperScan("com.pbtd.launcher.mapper")
public class LauncherDataSourceConfig {
	@Bean
	@ConfigurationProperties("spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public ServletRegistrationBean druidStatViewServlet() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		// registrationBean.addInitParameter("allow", "127.0.0.1");//IP白名单,(没有配置或者为空，则允许所有访问)
		registrationBean.addInitParameter("deny", "");// IP黑名单,(存在共同时，deny优先于allow)
		registrationBean.addInitParameter("loginUsername", "druid_admin");
		registrationBean.addInitParameter("loginPassword", "druid_admin123456");
		registrationBean.addInitParameter("resetEnable", "true");// 是否关闭清空按钮，true为不关闭
		return registrationBean;
	}

	// @Bean
	// public FilterRegistrationBean druidWebStatViewFilter() {
	// FilterRegistrationBean registrationBean = new FilterRegistrationBean(new
	// WebStatFilter());
	// registrationBean.addInitParameter("urlPatterns", "/druid/*");
	// registrationBean.addInitParameter("exclusions",
	// "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
	// return registrationBean;
	// }
}
