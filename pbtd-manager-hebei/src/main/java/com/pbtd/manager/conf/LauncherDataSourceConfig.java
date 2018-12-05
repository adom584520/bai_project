package com.pbtd.manager.conf;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 数据库配置
 * 
 * @author JOJO
 *
 */
@Configuration
@MapperScan(basePackages = "com.pbtd.manager.launcher.mapper", sqlSessionTemplateRef = "launcherSqlSessionTemplate")
public class LauncherDataSourceConfig {

	@Bean(name = "launcherDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.launcher")
	public DataSource systemDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "launcherSqlSessionFactory")
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("launcherDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/launcher/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "launcherTransactionManager")
	public DataSourceTransactionManager testTransactionManager(@Qualifier("launcherDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "launcherSqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("launcherSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
