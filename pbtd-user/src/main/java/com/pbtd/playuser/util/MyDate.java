package com.pbtd.playuser.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
	private MyDate() {
	};

	// 默认日期格式
	public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";
	// 默认时间格式
	public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String getNowDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
		return formatter.format(new Date());
	}
}
