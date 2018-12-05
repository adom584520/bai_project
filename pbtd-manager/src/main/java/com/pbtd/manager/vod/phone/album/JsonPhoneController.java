package com.pbtd.manager.vod.phone.album;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 手机节目集获取汇聚信息
 * @author zr
 */
@Controller 
@RequestMapping("/vod/phone/JsonPhone")
public class JsonPhoneController {
		
		    //获取当天更新数据到中心运营平台
    	    @RequestMapping(value = "/getjsoncurrenttime")
		    public int getjsoncurrenttime(){
		    	String requestUrl = "http://192.168.0.36/integrate/strategy/phonealbumsinfo";
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
