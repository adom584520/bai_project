package com.yh.push.utils;

import java.io.FileInputStream;   
import java.io.FileOutputStream;   
import java.util.Properties;   
  
public class PropertyEditor {   
    public static void main(String[] args) throws Exception {   
        Properties prop = new Properties();// 属性集合对象   
        FileInputStream fis = new FileInputStream("prop.properties");// 属性文件输入流   
        prop.load(fis);// 将属性文件流装载到Properties对象中   
        fis.close();// 关闭流   
  
        // 获取属性值，sitename已在文件中定义   
        System.out.println("获取属性值：sitename=" + prop.getProperty("sitename"));   
        // 获取属性值，country未在文件中定义，将在此程序中返回一个默认值，但并不修改属性文件   
        System.out.println("获取属性值：country=" + prop.getProperty("country", "中国"));   
  
        // 修改sitename的属性值   
        prop.setProperty("sitename", "Boxcode");   
        // 添加一个新的属性studio   
        prop.setProperty("studio", "Boxcode Studio");   
        // 文件输出流   
        FileOutputStream fos = new FileOutputStream("prop.properties");   
        // 将Properties集合保存到流中   
        prop.store(fos, "Copyright (c) Boxcode Studio");   
        fos.close();// 关闭流   
    }   
}  