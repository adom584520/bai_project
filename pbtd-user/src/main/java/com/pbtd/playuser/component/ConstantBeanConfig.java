package com.pbtd.playuser.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:config/vodConstant.properties" })
public class ConstantBeanConfig {
	@Value("${charset}")
	public String charset;// 接口编码
	@Value("${param}")
	public String param;// 参数名称
	@Value("${query_series_phone_url}")
	public String querySeriesPhoneUrl;// 通过专辑ID查询接口url-手机
	@Value("${query_series_tv_url}")
	public String querySeriesTvUrl; // 通过专辑ID查询接口url-TV
	@Value("${max_probability}") 
	public Integer maxProbability;// 转盘活动最大中奖概率
	@Value("${total}") 
	public Integer total;// 点播收藏和播放记录最大查看条数
	@Value("${flux_number}") 
	public Integer fluxNumber;// 点播收藏和播放记录最大查看条数
	
	public static String LOCAL_URL;// 本项目URL
	public static Long PLAY_TIME;//播放时长
	
	@Value("${localhost_url}")
	public void setLOCAL_URL(String picture_ip) {
		LOCAL_URL = picture_ip;
	}
	
	@Value("${play_time}")
	public void setPLAY_TIME(Long play_time) {
		PLAY_TIME = play_time;
	}
}
