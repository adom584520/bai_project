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
@MapperScan(basePackages = {"com.pbtd.manager.vodOnlinelibrary.mapper"}, sqlSessionTemplateRef = "vodOnlinelibrarySqlSessionTemplate")
public class VodOnlinelibraryDataSourceConfig {

	@Bean(name = "vodOnlinelibraryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.vodOnlinelibrary")
	public DataSource systemDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "vodOnlinelibrarySqlSessionFactory")
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("vodOnlinelibraryDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/Onlinelibrary/*/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "vodOnlinelibraryTransactionManager")
	public DataSourceTransactionManager testTransactionManager(@Qualifier("vodOnlinelibraryDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "vodOnlinelibrarySqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("vodOnlinelibrarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
