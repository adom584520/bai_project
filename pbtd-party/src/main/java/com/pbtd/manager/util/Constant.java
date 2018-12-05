package com.pbtd.manager.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Constant {
	private Constant() {
	}

	/**
	 * session的过期时间
	 */
	public static Integer sessionActiveTime = 7200; // 默认时间
	
	private static final String CONSTANT_PROPERTIES_PATH = "config/constant.properties";

	/**
	 * 
	 */
	private static Properties prop = new Properties();
	static {
		String path = Constant.class.getResource("/").getPath();
		try (InputStream in = new FileInputStream(path + CONSTANT_PROPERTIES_PATH);) {
			prop.load(in);
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void init() {
		String sat = prop.getProperty("mySessionActiveTime", "7200");
		sessionActiveTime = Integer.parseInt(sat);
	}
}
