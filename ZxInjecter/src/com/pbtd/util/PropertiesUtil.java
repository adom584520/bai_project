package com.pbtd.util;

import java.io.IOException;
import java.util.Properties;


public class PropertiesUtil {
	
	private static Properties properties = new Properties();
	
	static{
		try {
			properties.load(PropertiesUtil.class.getResourceAsStream("/configure.properties"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  static String getValue(String key){
		return properties.getProperty(key);
	}
	
	
	public static void main(String[] args) {
		PropertiesUtil prop = new PropertiesUtil();
		System.out.println(prop.getValue("name"));
		//System.out.println(propertiesUtil.getValue("name"));
	}

}
