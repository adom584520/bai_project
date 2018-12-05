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
@MapperScan(basePackages = {"com.pbtd.manager.vod.*.mapper","com.pbtd.manager.inject.*.mapper","com.pbtd.manager.vod.*.*.mapper"}, sqlSessionTemplateRef = "vodSqlSessionTemplate")
public class VodDataSourceConfig {

	@Bean(name = "vodDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.vod")
	public DataSource systemDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "vodSqlSessionFactory")
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("vodDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/vod*/*/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "vodTransactionManager")
	public DataSourceTransactionManager testTransactionManager(@Qualifier("vodDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "vodSqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("vodSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
