package com.pbtd.playclick.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 读取资源文件
 * 
 * @author yahai.hu 2011/11/9
 * 
 */
public class PropertyUtil {
	private Properties properties;

	public PropertyUtil() {
		this.properties = new Properties();
	}

	public PropertyUtil(String path) throws Exception {
		this();
		this.load(path);
	}

	public void load(String path) throws Exception {
		// 获取绝对路径
		File f = new File(this.getClass().getResource("/").getPath());
		String file1 = f.getPath().replaceAll("target", "");
		String file = file1.replaceAll("classes", "");
		f = new File(file + System.getProperty("file.separator") + path);
		//System.out.println(f.toPath() + "....");
		/*
		 * InputStream inputStream =
		 * PropertiesFile.class.getClassLoader().getResourceAsStream(path);
		 */
		properties.clear();
		try {
			properties.load(new FileInputStream(f));
			/*
			 * properties.load(inputStream);
			 */
		} catch (Exception e) {
			throw e;
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public boolean getBooleanProperty(String key) {
		String value = this.getProperty(key);
		if (null == value) {
			return false;
		} else {
			if (value.equalsIgnoreCase("true")) {
				return true;
			}
		}
		return false;
	}

	public Enumeration<?> getPropertyNames() {
		return properties.propertyNames();
	}

	// 写入Properties信息
	public void WriteProperties(String path, String pKey, String pValue) throws IOException {
		// 获取绝对路径
		File f = new File(this.getClass().getResource("/").getPath());
		String file1 = f.getPath().replaceAll("target", "");
		String file = file1.replaceAll("classes", "");
		f = new File(file + System.getProperty("file.separator") + path);
		Properties pps = new Properties();
		// 从输入流中读取属性列表（键和元素对）
		// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
		// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
		OutputStream out = new FileOutputStream(f);
		pps.setProperty(pKey, pValue);
		// 以适合使用 load 方法加载到 Properties 表中的格式，
		// 将此 Properties 表中的属性列表（键和元素对）写入输出流
		pps.store(out, "Update " + pKey + " name");
	}
	private String getYesterday() {
		// 获取当前日期的前一天
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat H = new SimpleDateFormat("HH");
		String timespan = H.format(new Date());
		int i = Integer.parseInt(timespan);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (i == 3) {
			calendar.add(Calendar.DATE, -0);
		} else {
			calendar.add(Calendar.DATE, -1);
		}
		date = calendar.getTime();
		return sdf.format(date);
	}

	public static void main(String args[]) throws Exception {
		PropertyUtil pro = new PropertyUtil("src/main/resources/config/yinhe.properties");
		System.out.println(pro.getProperty("key"));
		if (!"null".equals(pro.getProperty("key"))) {
			System.out.println("123");
		}
		//pro.WriteProperties("src/main/resources/config/yinhe.properties", "key", "null");
	}

}
