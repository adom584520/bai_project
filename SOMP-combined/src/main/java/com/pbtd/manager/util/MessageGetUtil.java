package com.pbtd.manager.util;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

public class MessageGetUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageGetUtil.class);
	private MessageGetUtil(){};
	public static HashMap<String,String> message = new HashMap<>(); 
	public static void loadProp(){
		Properties prop = new Properties();
		String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
		try (InputStreamReader in = new InputStreamReader(new FileInputStream(path + "/config/message.properties"),"UTF-8");) {
			prop.load(in);
		} catch (Exception e) {
			LOGGER.error("读取message.properties文件出错！",e);
		}
	    propertiesToMap(prop);
	    LOGGER.info("加载message.properties文件内容完成...........");
	}
	private static void propertiesToMap(Properties prop){
		  Enumeration<Object> keys = prop.keys();
	       while(keys.hasMoreElements()){
	    	   String elem = (String)keys.nextElement();
	    	   String val = (String)prop.get(elem);
	    	   message.put(elem, val);
	       }
	}
}
