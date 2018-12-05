package com.pbtd.launcher.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	// 将日期转为纯数字格式方法
	public static String date2Num(Date date) {
		String pattern = "yyyy-MM-dd-HH-mm-ss";
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return remove(sdf.format(date));
	}
	//删除字符串中的“-”符号
	private static String remove(String patternDate){
		return patternDate.replaceAll("-", "");
	}
}