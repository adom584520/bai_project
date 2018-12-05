package com.pbtd.playuser.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库配置
 * 
 * @author JOJO
 *
 */
@Configuration
@MapperScan("com.pbtd.playuser.mapper")
public class UserDataSourceConfig {
}
