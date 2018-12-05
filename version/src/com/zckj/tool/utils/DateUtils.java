/**
 * @author liuxiaomeng
 * @datetime 2015-8-4_下午1:15:56
 */
package com.zckj.tool.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期各种格式转换
 * 
 * @author liuxiaomeng
 * @datetime 2015-8-4_下午1:15:56
 */
public class DateUtils {
	public static final String FORMAT_TYPE_1 = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_TYPE_2 = "yyyy年MM月dd日 HH时mm分ss秒";

	public static final String FORMAT_TYPE_3 = "YYYYMMdd";

	/**
	 * date类型转换为String类型 formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒 data Date类型的时间
	 */
	public static String dateToString(final Date data, final String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

	/**
	 * long类型转换为String类型 currentTime要转换的long类型的时间 formatType要转换的string类型的时间格式
	 */
	public static String longToString(final long currentTime, final String formatType) throws ParseException {
		final Date date = DateUtils.longToDate(currentTime, formatType); // long类型转成Date类型
		final String strTime = DateUtils.dateToString(date, formatType); // date类型转成String
		return strTime;
	}

	/**
	 * string类型转换为date类型 strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒， strTime的时间格式必须要与formatType的时间格式相同
	 */
	public static Date stringToDate(final String strTime, final String formatType) throws ParseException {
		final SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	/**
	 * long转换为Date类型 currentTime要转换的long类型的时间 formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 */
	public static Date longToDate(final long currentTime, final String formatType) throws ParseException {
		final Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		final String sDateTime = DateUtils.dateToString(dateOld, formatType); // 把date类型的时间转换为string
		final Date date = DateUtils.stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

	/**
	 * string类型转换为long类型 strTime要转换的String类型的时间 formatType时间格式 strTime的时间格式和formatType的时间格式必须相同
	 */
	public static long stringToLong(final String strTime, final String formatType) throws ParseException {
		final Date date = DateUtils.stringToDate(strTime, formatType); // String类型转成date类型
		if (date == null) {
			return 0;
		} else {
			final long currentTime = DateUtils.dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	/**
	 * date类型转换为long类型 date要转换的date类型的时间
	 */
	public static long dateToLong(final Date date) {
		return date.getTime();
	}

	/**
	 * @author liuxiaomeng
	 * @datetime 2015-8-4_下午1:15:56
	 * @param args
	 */
	public static void main(final String[] args) {
		// TODO 需要完善相关逻辑
	}
}
