
package com.pbtd.playclick.base.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pbtd.playclick.base.common.util.md5.MD5;

/**
 * 参数校验类，提供简单的参数校验能力
 *  主要目的是将分散的判断统一到一起
 *  ，同时也减少人为的低级错误
 * @Title: ParamTools.java
 * @Copyright: Copyright (c) 2011
 * @Company: kuntuo
 * @author xujian
 * @version 1.0 2011-07-08
 */
public class ParamTools
{
    private static final int ZERO = 0;

    private static final String EMPTY = "";

    /**
     * 判断对象是否为NULL
     * @param object 测试对象
     * @return boolean 对象等于null返回true
     */
    public static final boolean isObjectNotNull(Object object)
    {
        return (boolean) (null != object);
    }

    /**
     * 判断List是否为NULL或长度为0
     * @param list 检测列表
     * @return boolean 
     */
    public static final boolean isListNullOrEmpty(List<Object> list)
    {
        return (boolean) (null == list || ZERO == list.size());
    }

    /**
     * 判断String是否为NULL或""
     * @param str 检测对象
     * @return boolean
     */
    public static final boolean isStringNullOrEmpty(String str)
    {
        return (boolean) (null == str || EMPTY.equals(str.trim()));
    }

    /**
     * 判断Map是否为NULL或长度为0
     * @param map 检测对象
     * @return boolean
     */
    @SuppressWarnings("rawtypes")
    public static final boolean isMapNullOrEmpty(Map map)
    {
        return (boolean) (null == map || ZERO == map.size());
    }

    /**
     * 判断Object是否为NULL或长度为0
     * @param array 测试数组对象
     * @return boolean
     */
    public static final boolean isObjectArrayNullOrEmpty(Object[] array)
    {
        return (boolean) (null == array || ZERO == array.length);
    }

    /**
     * 判断String[]是否为NULL或长度为0
     * @param array 数组对象
     * @return boolean
     */
    public static final boolean isStringArrayNullOrEmpty(String[] array)
    {
        return (boolean) (null == array || ZERO == array.length);
    }

    /**
     * 判断byte[]是否为NULL或长度为0
     * @param array 数组对象
     * @return boolean
     */
    public static final boolean isByteArrayNullOrEmpty(byte[] array)
    {
        return (boolean) (null == array || ZERO == array.length);
    }

    /**
     * 判断short[]是否为NULL或长度为0
     * @param array 数组short对象
     * @return boolean
     */
    public static final boolean isShortArrayNullOrEmpty(short[] array)
    {
        return (boolean) (null == array || ZERO == array.length);
    }

    /**
     * 判断int[]是否为NULL或长度为0
     * @param array 对象array
     * @return boolean
     */
    public static final boolean isIntArrayNullOrEmpty(int[] array)
    {
        return (boolean) (null == array || ZERO == array.length);
    }

    /**
     * 判断long[]是否为NULL或长度为0
     * @param array 长整型数组对象
     * @return boolean
     */
    public static final boolean isLongArrayNullOrEmpty(long[] array)
    {
        return (boolean) (null == array || ZERO == array.length);
    }

    /**
     * 判断float[]是否为NULL或长度为0
     * @param array 浮点型数组对象
     * @return boolean
     */
    public static final boolean isFloatArrayNullOrEmpty(float[] array)
    {
        return (boolean) (null == array || ZERO == array.length);
    }

    /**
     * 判断double[]是否为NULL或长度为0
     * @param array 双精度数组
     * @return boolean
     */
    public static final boolean isDoubleArrayNullOrEmpty(double[] array)
    {
        return (boolean) (null == array || ZERO == array.length);
    }

    /**
     * 通过正则表达式检查IP地址是否正确
     * 
     * @return ip地址是否正确
     * @param ip String
     */
    public static boolean isValidIp(String ip)
    {
        String regex = "^((((\\d{1,2})|(1\\d{0,2})|(2[0-4][0-9])|(25[0-5]))\\.)"
                + "{3}((\\d{1,2})|(1\\d{0,2})|(2[0-4][0-9])|(25[0-5])))$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * 检查端口号是否正确, 入参为Int
     * 
     * @return 端口号是否正确
     * @param port int
     */
    public static boolean isNumThanZero(int num)
    {
        return ZERO < num;
    }

    /**
     * 测试字符串二进制最大长度
     * @param string 测试的字符串
     * @param length 比较的长度
     * @return boolean
     */
    public static boolean isStringBeyondMaxLength(String string , int length)
    {
        return length < string.length();
    }

    /**
     * 测试字符串二进制最小长度
     * @param string 测试字符串
     * @param length 比较长度
     * @return boolean
     */
    public static boolean isStringBeyondMinLength(String string , int length)
    {
        return (boolean) (length > string.length());
    }

    /**
     * 通过正则表达式检查是否是数字字符串
     * 
     * @return 是否是数字字符串
     * @param str String
     */
    public static boolean isValidNumberString(String str)
    {
        String regex = "^[0-9]+$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /***************************************************************************
     * 将逗号分割的字符串组装成List集合 
     * @param idsStr 逗号分割的字符串 
     * @return List集合
     */
    public static List<String> buildIds(String idsStr)
    {
        List<String> idItems = new ArrayList<String>();
        String[] objectIds = null;
        if (StringUtils.isNotEmpty(idsStr))
        {
            objectIds = idsStr.split(",");
            for (String id : objectIds)
            {
                idItems.add(id);
            }
        }
        return idItems;
    }

    /***
     * 去除List中重复的数据 
     * @param list 需要处理List
     * @return 返回处理完的List
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<String> removeDuplicateString(List<String> list)
    {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /***
     * 去除List中重复的数据 
     * @param list 需要处理List
     * @return 返回处理完的List
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Map<String, Object>> removeDuplicateMap(List<Map<String, Object>> list)
    {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }


    /***
     * 将两个字符相加并以逗号分割 
     * @param fristStr 第一个字符串
     * @param secendStr 第二个字符串
     * @return 结果
     */
    public static String buildIdStr(String fristStr , String secendStr)
    {
        if (StringUtils.isNotBlank(fristStr) && StringUtils.isNotBlank(secendStr))
        {
            return fristStr + "," + secendStr;
        }
        else
        {
            return fristStr + secendStr;
        }
    }

    /**
     * 使用MD5算法进行加密
     * @param sourcePassword 待加密的明文密码
     * @return 返回加密后的字符串。32位。
     */
    public static String encodedPassword(String sourcePassword)
    {
        String unionPassword = "";
        MD5 md = new MD5();
        if (StringUtils.isNotBlank(sourcePassword))
        {
            unionPassword = new String(sourcePassword);
            md.Update(unionPassword);
            unionPassword = md.asHex();
        }
        return unionPassword.toUpperCase();
    }
}
