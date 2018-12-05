package com.pbtd.manager.user.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormaUtil {

	//	public static void main(String[] args) {
	//		Date date = new Date();
	//		System.out.println(yesterdayString(date));
	//	}
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 


	/**
	 * 返回前天
	 * @param today
	 * @return
	 */
	public static String beforeyestodayString(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 2);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
		return yesterday;
	}
	/**
	 * 返回昨天
	 * @param today
	 * @return
	 */
	public static String yestodayString(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
		return yesterday;
	}
	/**
	 * 返回明天
	 * @param today
	 * @return tomorrow 
	 */
	public static String tomorrowString(String today) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(today);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day1 = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day1 + 1);
		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}


	/**
	 * 返回当月第一天
	 * @param today
	 * @return
	 */
	public static String thismouthfirstString(Date today) {
		Calendar c = Calendar.getInstance();    
		c.setTime(today);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		String first = format.format(c.getTime());
		return first;
	}
	/**
	 * 返回今天字符串
	 * @param today
	 * @return
	 */
	public static String todayString(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 0);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(calendar.getTime());
		return yesterday; 
	}
	/**
	 * 返回昨天
	 * @param today
	 * @return
	 */
	public static Date yesterday(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		return calendar.getTime();
	}

	/**
	 * 返回当天 00:00:00开始时间
	 * @param 
	 * @return
	 */
	public static Date getNowDayStart(Date now) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(now);
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format2.parse(str+" 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}



	/**
	 *字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate,String bdate) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();  
		cal.setTime(sdf.parse(smdate));  
		long time1 = cal.getTimeInMillis();               
		cal.setTime(sdf.parse(bdate));  
		long time2 = cal.getTimeInMillis();       
		long between_days=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(between_days));   
	}
	
/*	public static void main(String[] args) {
		try {
			System.out.println(daysBetween("2018-10-11 00:00:11","2018-10-15 22:24:23"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
