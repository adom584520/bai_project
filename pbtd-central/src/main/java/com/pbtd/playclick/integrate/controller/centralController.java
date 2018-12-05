package com.pbtd.playclick.integrate.controller;

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
 *中心运营平台接口
 * @author zr
 *
 */
@Configuration
@PropertySource(value = {"classpath:config/central.properties" })
public class centralController {
	public static Logger log = Logger.getLogger(centralController.class);
	 /**
	  * 国广地址
	  */
	@Value("${gg_album}")
	public String        gg_album;
	 @Value("${gg_albuminfo}")
	public String		gg_albuminfo;
 
	/**
	 * 银河地址
	 */
		//#自动汇聚phone专辑变更路径
		@Value("${central_url}")
		public String central_url;
		 
		
		//#手机端 频道路径	`
		@Value("${phone_channel}")
		public String phone_channel;
		//#手机端标签路径
		@Value("${phone_label}")
		public String phone_label;
		
		//#手机  人工下发专辑路径
		@Value("${phone_albumurl}")
		public String phone_albumurl;
		 
		
		//#TV端 频道路径
		@Value("${tv_channel}")
		public String tv_channel;
		 
		
		//#演员路径
		@Value("${actors}")
		public String actors;

		
		public int gethttp(String requestUrl){
			//String requestUrl =central.actors;
			String requestMethod = "GET";
			String jsonObject = null;
			StringBuffer buffer = new StringBuffer();
			InputStream inputStream = null;
			try {
				URL url = new URL(requestUrl);
				HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
				httpUrlConn.setDoOutput(true);
				httpUrlConn.setDoInput(true);
				httpUrlConn.setUseCaches(false);
				// 设置请求方式（GET/POST）
				httpUrlConn.setRequestMethod(requestMethod);
				if ("GET".equalsIgnoreCase(requestMethod))
					httpUrlConn.connect();
				// 将返回的输入流转换成字符串
				inputStream = httpUrlConn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				inputStreamReader.close();
				// 释放资源
				inputStream.close();
				inputStream = null;
				httpUrlConn.disconnect();
				jsonObject=buffer.toString() ;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return 0;
			} catch (ProtocolException e) {
				e.printStackTrace();
				return 0;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return 0;
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
			return 1;
		}
		
}
