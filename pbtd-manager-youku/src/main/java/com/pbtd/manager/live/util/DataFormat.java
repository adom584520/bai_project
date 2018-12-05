package com.pbtd.manager.live.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataFormat {
	    /**
	     * date类型进行格式化输出
	     * @param date
	     * @return
	     */
	    public static String dateFormat(Date date) {
	          SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	          String dateString = formatter.format(date);
	          return dateString;
	     }
	    
		/**
		 *
		 * 相对当前时间的前后天数 返回日期 String
		 * 
		 * @param fix：
		 * @return
		 */
		public static String getCurrentDate(int fix) {
			Date date = new Date(System.currentTimeMillis() + fix * 24 * 60 * 60 * 1000);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd");
			return simpleDateFormat.format(date);
		}
	    /**
	     * 将"2015-08-31 21:08:06"型字符串转化为Date
	     * @param str
	     * @return
	     * @throws ParseException
	     */
	    public static Date StringToDate(String str) throws ParseException{
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = (Date) formatter.parse(str);
	        return date;
	    }

	    /**
	     * 将CST时间类型字符串进行格式化输出
	     * @param str
	     * @return
	     * @throws ParseException
	     */
	    public static String CSTFormat(String str) throws ParseException{
	            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	            Date date = (Date) formatter.parse(str);
	            return dateFormat(date);
	    }
	    /**
	     * 将long类型转化为Date
	     * @param str
	     * @return
	     * @throws ParseException
	     */
	    public static Date LongToDare(long str) throws ParseException{      
	        return new Date(str * 1000);  
	    }


}
