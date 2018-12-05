package com.yh.push.utils;

import java.io.File;
import java.io.FileInputStream;
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
	
	public PropertyUtil(){
		this.properties = new Properties();
	}
	
	public PropertyUtil(String path) throws Exception{
		this();
		this.load(path);
	}
	
	public void load(String path)throws Exception{
		//获取绝对路径
		File f = new File(this.getClass().getResource("/").getPath());
		String  file= f.getPath().replaceAll("classes","");
		//System.out.println(file);
		f = new File(file+System.getProperty("file.separator")+path);
		/*
		InputStream inputStream = PropertiesFile.class.getClassLoader().getResourceAsStream(path);
		*/
		properties.clear();
		try{
			properties.load(new FileInputStream(f));
			/*
			properties.load(inputStream);
			 */
		}catch(Exception e){
			throw e;
		}
	}
	
	public String getProperty(String key){
		return properties.getProperty(key);
	}
	
	public boolean getBooleanProperty(String key){
		String value = this.getProperty(key);
		if(null==value){
			return false;
		} else {
			if( value.equalsIgnoreCase("true") ){
				return true;
			}
		}
		return false;
	}
	
	public Enumeration<?> getPropertyNames(){
		return properties.propertyNames();
	}
	
	/*public static void main(String args[]) throws Exception{
	
			PropertyUtil pro = new PropertyUtil("app-config/push.properties");
			System.out.println( pro.getProperty("TV.appkey") );
	}*/
}
