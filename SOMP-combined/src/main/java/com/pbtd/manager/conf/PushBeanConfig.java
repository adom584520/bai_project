package com.pbtd.manager.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:config/push.properties"})
public class PushBeanConfig {
	// 安卓手机应用唯一标识appkey
	@Value("${Android.appkey}")
	public String AndroidAppkey;
	// 安卓手机appMasterSecret
	@Value("${Android.appMasterSecret}")
	public String AndroidAppMasterSecre;
	// IOS应用唯一标识appkey
	@Value("${IOS.appkey}")
	public String IOSAppkey;
	// IOS appMasterSecret
	@Value("${IOS.appMasterSecret}")
	public String IOSAppMasterSecret;
	// 1为开发者模式,不为1是生产模式
	@Value("${IOS_Mode}")
	public String IOS_Mode;
	// TV应用唯一标识appkey
	@Value("${TV.appkey}")
	public String TVAppkey;
	// TV appMasterSecret
	@Value("${TV.appMasterSecret}")
	public String TVAppMasterSecret;

}
