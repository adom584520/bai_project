package com.pbtd.manager.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {

	/**
	 * 得到一天的最后一秒钟
	 * 
	 * @param d
	 * @return
	 */
	public static Date endOfDay(Date d) {
		return DateUtils.addSeconds(DateUtils.addDays(DateUtils.truncate(d, Calendar.DATE), 1), -1);
	}

	/**
	 * 两个时间的间隔秒
	 * 
	 * @return
	 */
	public static long secondsBetween(Date d1, Date d2) {
		return Math.abs((d1.getTime() - d2.getTime()) / 1000);
	}

	// 将日期转为纯数字格式方法
	public static String date2Num(Date date) {
		String pattern = "yyyy-MM-dd-HH-mm-ss";
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return remove(sdf.format(date));
	}

	// 删除字符串中的“-”符号
	private static String remove(String patternDate) {
		return patternDate.replaceAll("-", "");
	}
}