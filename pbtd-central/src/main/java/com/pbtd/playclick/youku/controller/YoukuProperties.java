package com.pbtd.playclick.youku.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *获取优酷接口资源
 * @author zr
 *
 */
@Configuration
@PropertySource(value = {"classpath:config/youku.properties" })
public class YoukuProperties {
	public static Logger log = Logger.getLogger(YoukuProperties.class);

	
	//是否保存优酷返回数据文件
	@Value("${issavefile}") 	public int issavefile;

	
	//优酷接口配置
	@Value("${url}")  public String url;

	//公共请求参数-----------------------------------------------start-------------------------------------
	//String	是	TOP分配给应用的AppKey。
	@Value("${app_key}")  public String app_key;
	@Value("${secret}")  public String secret;
	//公共请求参数-----------------------------------------------end-------------------------------------


	//------------------------------------------API接口名称 ----start--------------------------------------
	// (付费节目批量查询)
	@Value("${pay_method_showpage}")  public String pay_method_showpage;
	//Number	可选	20	默认值：20分页大小，不超过100
	@Value("${pay_showpage_page_size}")  public Long pay_showpage_page_size;
	
	
	//(批量获取付费视频信息)
	@Value("${pay_method_videopage}")  public String pay_method_videopage;
	//Number	可选	10		每页条数
	@Value("${pay_videopage_page_size}")  public Long 	 pay_videopage_page_size	;
	
	
	//#(视频分页数据输出)
	@Value("${method_videopage}")  public String method_videopage;
	// #Number	可选	10		每页条数
	@Value("${videopage_page_size}")  public Long videopage_page_size;

	//------------------------------------------API接口名称 ----end--------------------------------------


}
