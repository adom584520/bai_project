package com.pbtd.manager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyDateUtil {
	private static final Logger logger = LoggerFactory.getLogger(MyDateUtil.class);

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

	/**
	 * 将字符串格式化为指定的日期格式字符串
	 * 
	 * @param date
	 *            日期
	 * @param dateFormat
	 *            日期格式
	 * @param format
	 *            转换格式
	 * @return
	 */
	public static String stringDateFormater(String date, String dateFormat, String format) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
		Date newDate = null;
		try {
			newDate = dateFormatter.parse(date);
			date = dateToString(newDate, format);
		} catch (ParseException e) {
			logger.warn("系统管理-日期工具-日期解析失败", e);
		}
		return date;
	}

	/**
	 * 将日期转为对应的字符串格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		String dateFormat = null;
		try {
			dateFormat = dateFormatter.format(date);
		} catch (Exception e) {
			logger.warn("系统管理-日期工具-日期转换失败", e);
		}
		return dateFormat;
	}

	/**
	 * 将字符串格式化为指定的日期格式字符串，并且为当天的最后时间
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            转换格式
	 * @return
	 */
	public static String endOfDayStringDateFormater(String date, String dateFormat, String format) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
		Date newDate = null;
		try {
			newDate = dateFormatter.parse(date);
			date = dateToString(endOfDay(newDate), format);
		} catch (ParseException e) {
			logger.warn("系统管理-日志搜索功能-日期解析失败", e);
		}
		return date;
	}

	/**
	 * 获取当前一个月前的时间
	 * @return
	 */
	public static String getBeforeMonth(String formatter) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(formatter);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Date m = c.getTime();
		String mon = dateFormatter.format(m);
		return mon;
	}
	/**
	 * 根据格式格式化时间
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String formatter(Date date,String formatter) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(formatter);
		return dateFormatter.format(date);
	}
}