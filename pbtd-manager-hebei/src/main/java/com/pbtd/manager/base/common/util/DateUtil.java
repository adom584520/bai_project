
package  com.pbtd.manager.base.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class DateUtil
{

    public static String dateFormat1 = "yyyy-MM-dd";

    public static String dateFormat2 = "yyyy-MM-dd HH:mm:ss";

    public static String dateFormat3 = "yy-MM-dd HH:mm";

    public static String dateFormat4 = "dd/MM/yyyy";

    public static String dateFormat5 = "yyyyMMddHHmmss";

    public static void main(String[] args) throws ParseException
    {

        System.out.println(DateUtil.daysBetween("2013-02-1", "2013-02-4"));
    }

    public static Long timeStrTran1970Seconds(String timeStr) throws java.text.ParseException
    {
        Date stat = null;
        stat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(timeStr);
        if (stat != null)
            return stat.getTime() / 1000;
        else
            return 0l;
    }

    public static String getFormatDate(Date date,String format){
    	if(format==null||format.length()==0){
    		format = dateFormat2;
    	}
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    	return simpleDateFormat.format(date);
    }
    
    public static String getDateStrByStr(String dateParam)
    {

        String dateStr = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            dateStr = sf.format(sf.parse(dateParam));
        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }

        return dateStr;
    }

    /**
     * 方法描述：获取当前日期的本年第一天的日期
     *
     * 作者：weng
     * 当前版本： v1.0 
     * @param date
     * @return
     */
    public static String getFirstDayOfYear(Date date)
    {

        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.MONTH, 0);// 月份是从0开始计的
        curr.set(Calendar.DAY_OF_MONTH, 1);
        date = curr.getTime();
        String dateStr = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = sf.format(date);
        return dateStr;
    }

    /**
     * 方法描述：获取当前日期的本季度第一天的日期
     *
     * 作者：weng
     * 当前版本： v1.0 
     * @param date
     * @return
     */
    public static String getFirstDayOfJidu(Date date)
    {

        int month = getMonthByDate(date);//获取本月
        int firstMonthOfJiDu = 1;
        //注意:Calendar的月份是从0开始计的
        if (month >= 1 && month <= 3)
        {
            //第一季度
            firstMonthOfJiDu = 0;
        }
        else if (month >= 4 && month <= 6)
        {
            //第二季度
            firstMonthOfJiDu = 3;
        }
        else if (month >= 7 && month <= 9)
        {
            //第三季度
            firstMonthOfJiDu = 6;
        }
        else if (month >= 10 && month <= 12)
        {
            //第三季度
            firstMonthOfJiDu = 9;
        }
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.MONTH, firstMonthOfJiDu);
        curr.set(Calendar.DAY_OF_MONTH, 1);
        date = curr.getTime();
        String dateStr = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = sf.format(date);
        return dateStr;
    }

    /**
     * 方法描述：获取当前日期的本周的一号的日期
     *
     * 作者：weng
     * 当前版本： v1.0 
     * @param date
     * @return
     */
    public static String getFirstDayOfWeek(Date date)
    {

        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.DAY_OF_WEEK, 1);
        date = curr.getTime();
        String dateStr = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = sf.format(date);
        return dateStr;
    }

    /**
     * 方法描述：获取当前日期的本月的一号的日期
     *
     * 作者：weng
     * 当前版本： v1.0 
     * @param date
     * @return
     */
    public static String getFirstDayOfMonth(Date date)
    {

        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.DAY_OF_MONTH, 1);
        date = curr.getTime();
        String dateStr = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = sf.format(date);
        return dateStr;
    }

    /**
     * 方法描述：把获取当前或以后几月以前的日期，并返回字符串
     *
     * 作者：weng
     * 当前版本： v1.0 
     * @param formatStr格式
     * @param monthCount获取几月之前的日期,+号表示几月之后，-号表示之月之前
     * @return
     */
    public static String getStringByMonthCount(Date date , String formatStr , int monthCount)
    {

        if (formatStr != null && "".equals(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) + monthCount);
        date = curr.getTime();
        String dateStr = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = sf.format(date);

        return dateStr;
    }

    /**
     * 方法描述：把获取当前或以后几天以前的日期，并返回字符串
     *
     * 作者：wengzhonghui
     * 当前版本： v1.0 
     * @param formatStr格式
     * @param dayCount获取几天之前的日期,+号表示几天之后，-号表示之天之前
     * @return
     */
    public static String getStringByDateCount(String dateStr , String formatStr , int dayCount)
    {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        if (formatStr != null && "".equals(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        Date date = new Date();
        try
        {
            date = sf.parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) + dayCount);
        date = curr.getTime();

        dateStr = sf.format(date);

        return dateStr;
    }

    /**
     * 方法描述：把获取当前或以后几天以前的日期，并返回字符串
     *
     * 作者：wengzhonghui
     * 当前版本： v1.0 
     * @param formatStr格式
     * @param dayCount获取几天之前的日期,+号表示几天之后，-号表示之天之前
     * @return
     */
    public static String getStringByDateCount(Date date , String formatStr , int dayCount)
    {

        if (formatStr != null && "".equals(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) + dayCount);
        date = curr.getTime();
        String dateStr = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = sf.format(date);

        return dateStr;
    }

    /**
     * 方法描述：获取两个时间之间相隔的天数
     *
     * 作者：weng
     * @param startDate开始时间
     * @param endDate结束时间
     * @param formatStr时间格式
     * @return
     */
    public static long getDayCountBetweenTwoDate(String startDate , String endDate , String formatStr)
    {

        if (formatStr != null && "".equals(formatStr))
        {
            formatStr = "yyyy-MM-dd";
        }
        SimpleDateFormat d = new SimpleDateFormat(formatStr);//格式化时间
        long result = -1;
        try
        {
            result = (d.parse(endDate).getTime() - d.parse(startDate).getTime()) / 3600000 / 24;//获取两个时间之间的天数
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * yy-mm-dd HH:mm:SS格式的时间
     * 
     * @return String Date
     */
    public static String getNowDateTime()
    {
        Date dateTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strTime = format.format(dateTime);
        return strTime;
    }

    public static String formatDate(Date dateTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strTime = format.format(dateTime);
        return strTime;
    }

    /**
     * yyyy-MM-dd HH-mm-ss格式的时间
     * 
     * @return String Date
     */
    public static String getNowDateTimeStr()
    {
        Date dateTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String strTime = format.format(dateTime);
        return strTime;
    }

    /**
     * yy-mm-dd格式的时间
     * 
     * @return StringDate
     */
    public static String getNowDate()
    {
        Date dateTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strTime = format.format(dateTime);
        return strTime;
    }

    /**
     * yy-mm-dd格式的时间
     * 
     * @return StringDate
     */
    public static String getBeforDate()
    {
        Date dateTime;
        try
        {
            dateTime = DateUtil.parse(getNowDate(), "yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);
            calendar.add(Calendar.DATE, -1);
            return DateUtil.format(calendar.getTime(), "yyyy-MM-dd");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * yy-mm-dd格式的时间
     * 
     * @return StringDate
     */
    public static String getNowMonth()
    {
        Date dateTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String strTime = format.format(dateTime);
        return strTime;
    }

    /**
     * yy-mm-dd格式的时间
     * 
     * @return StringDate
     */
    public static String getBeforMonth()
    {
        Date dateTime;
        try
        {
            dateTime = DateUtil.parse(getNowDate(), "yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);
            calendar.add(Calendar.MONTH, -1);
            return DateUtil.format(calendar.getTime(), "yyyy-MM");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * yy-mm-dd格式的时间
     * 
     * @return StringDate
     */
    public static String getNowYear()
    {
        Date dateTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String strTime = format.format(dateTime);
        return strTime;
    }

    /**
     * yy-mm-dd格式的时间
     * 
     * @return StringDate
     */
    public static String getBeforYear()
    {
        Date dateTime;
        try
        {
            dateTime = DateUtil.parse(getNowDate(), "yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);
            calendar.add(Calendar.YEAR, -1);
            return DateUtil.format(calendar.getTime(), "yyyy");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * dateFormat:yy-mm-dd格式的时间
     * 
     * @return StringDate
     */
    public static String getExpireDateTime(String dateFormat , int days)
    {

        Date dateTime = new Date();
        //log.debug(dateTime.getTime() - 24*3600*1000L*days);
        Date dtValue = new Date(dateTime.getTime() + 24 * 3600 * 1000L * days);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strTime = format.format(dtValue);
        return strTime;

    }

    /**
     * yy-mm-dd格式的时间
     * 
     * @return StringDate
     */
    public static String getStringDateShort()
    {
        Date dateTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strTime = format.format(dateTime);
        return strTime;
    }

    /**
     * yyyyMMddHHmmss格式的时间
     * 
     * @return StringDate
     */
    public static String getStringDateLong()
    {
        Date dateTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String strTime = format.format(dateTime);
        return strTime;
    }

    public static String formatNow(String dateformat)
    {
        SimpleDateFormat f = new SimpleDateFormat(dateformat);
        return f.format(new Date());
    }

    public static String format(String date , String dateformat)
    {
        SimpleDateFormat f = new SimpleDateFormat(dateformat);
        try
        {
            Date d = DateFormat.getDateInstance().parse(date);
            return f.format(d);
        }
        catch (Exception e)
        {
            return date;
        }
    }

    public static String getCurrentTimestamp()
    {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.toString();
    }

    /**
     * 获得当前时间long型
     * 
     * @return 当前时间long型
     */
    public static long getLongDate()
    {
        return System.currentTimeMillis();
    }

    /**
     * 获取基于当前日期后指定的days天后的日期
     * 
     * @param days
     * @return 前日期后指定的days天后的日期
     */
    public static String getExpireDate(String days)
    {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy年MM月dd日");
        return getExpireDate(simpledateformat.format(new Date()), days);
    }

    /**
     * 获取基于指定日期fromDate后指定的days天后的日期
     * 
     * @param fromDate
     * @param days
     * @return 指定日期fromDate后指定的days天后的日期
     */
    @SuppressWarnings("deprecation")
    public static String getExpireDate(String fromDate , String days)
    {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(defaultDatePattern);
        int i = Integer.parseInt(days);
        long l = (long) i * 0x5265c00L;
        long l1 = Date.parse(fromDate) + l;
        return simpledateformat.format(new Date(l1));
    }

    /**
     * 获取改日期的前一天
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay)
    {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try
        {
            date = new SimpleDateFormat(defaultDatePattern).parse(specifiedDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat(defaultDatePattern).format(c.getTime());
        return dayBefore;
    }

    /**
    * 获取该日期的后一天
    * @param specifiedDay
    * @return
    */
    public static String getSpecifiedDayAfter(String specifiedDay)
    {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try
        {
            date = new SimpleDateFormat(defaultDatePattern).parse(specifiedDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat(defaultDatePattern).format(c.getTime());
        return dayAfter;
    }

    private static String defaultDatePattern = "yyyy-MM-dd";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern()
    {
        return defaultDatePattern;
    }

    /**
     * 返回预设Format的当前日期字符串
     */
    public static String getToday()
    {
        Date today = new Date();
        return format(today);
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String format(Date date)
    {
        return date == null ? "" : format(date, getDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date , String pattern)
    {
        return date == null ? "" : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public static Date parse(String strDate)
    {
        Date date = null;
        try
        {
            date = parse(strDate, getDatePattern());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate , String pattern) throws ParseException
    {
        return new SimpleDateFormat(pattern).parse(strDate);
    }

    /**
     * 在日期上增加数个整月
     */
    public static Date addMonth(Date date , int n)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 判定该日期是否为周末
     */
    public static String isWeekend(String theDate)
    {
        String returnValue = "N";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date myDate = sdf.parse(theDate);
            Calendar myCalendar = Calendar.getInstance();
            myCalendar.setTime(myDate);
            int i = myCalendar.get(Calendar.DAY_OF_WEEK);
            // 星期日i==1，星期六i==7
            if (i == 1 || i == 7)
            {
                returnValue = "Y";
            }
        }
        catch (Exception ex)
        {
        }
        return returnValue;
    }

    /**
     * 方法描述：通过日期获取年份
     *
     * 作者： weng
     * 当前版本： v1.0 
     * @param date日期
     * @return
     */
    public static int getYearByDate(Date date)
    {

        Calendar cal = Calendar.getInstance();
        if (date == null)
        {
            date = new Date();
        }
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);

        return year;

    }

    /**
     * 方法描述：通过日期获取月份
     *
     * 作者： weng
     * 当前版本： v1.0 
     * @param date日期
     * @return
     */
    public static int getMonthByDate(Date date)
    {

        Calendar cal = Calendar.getInstance();
        if (date == null)
        {
            date = new Date();
        }
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        month++;//因为Calendar的月份是从0开始的
        return month;

    }

    /**
     * 方法描述：通过日期获取日
     *
     * 作者： weng
     * 当前版本： v1.0 
     * @param date日期
     * @return
     */
    public static int getDayByDate(Date date)
    {

        Calendar cal = Calendar.getInstance();
        if (date == null)
        {
            date = new Date();
        }
        cal.setTime(date);
        int year = cal.get(Calendar.DAY_OF_MONTH);
        return year;

    }

    /** 
     * 计算两个日期之间相差的天数 
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数 
     */
    public static int daysBetween(String smDateStr , String bDateStr)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat1);
        try
        {
            Date smDate = sdf.parse(smDateStr);
            Date bDate = sdf.parse(bDateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(smDate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bDate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return -1;

    }

    /**
     * 获得当前年
     * @return String 当前年
     */
    public static String getCurrentYear()
    {
        Calendar c = Calendar.getInstance();
        return String.valueOf(c.get(Calendar.YEAR));
    }

    /**
     * 获得当前月
     * @return String 当前年
     */
    public static String getCurrentMonth()
    {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        if (month < 10)
        {
            return "0" + month;
        }
        else
        {
            return String.valueOf(month);
        }
    }

    public static int monthDays(String args)
    {

        //或者用
        Calendar cal = Calendar.getInstance();

        /**设置date**/
        SimpleDateFormat oSdf = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
        try
        {
            cal.setTime(oSdf.parse(args));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        /**开始用的这个方法获取实际月的最大天数**/
        int days = cal.getActualMaximum(Calendar.DATE);

        return days;
    }

    /*
    * yy-mm-dd格式的时间
    * 
    * @return StringDate
    */
    public static String getNowDate2()
    {
        Date dateTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.dateFormat3);
        String strTime = format.format(dateTime);
        return strTime;
    }

    public static boolean compareDate(String startDate , String endDate)
    {
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate))
        {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                Date dt1 = df.parse(startDate);
                Date dt2 = df.parse(endDate);
                if (dt2.getTime() > dt1.getTime())
                {
                    return true;
                }
                else
                {
                    return false;
                }

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }
        return false;
    } 
}
