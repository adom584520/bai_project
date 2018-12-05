package com.pbtd.manager.util;

import java.util.List;

public class MyStringUtil {
	public static final String SEPARATOR = ",";
	public static final String TAILOR_LEFT_BRACKET = "[";
	public static final String TAILOR_RIGHT_BRACKET = "]";
	private MyStringUtil(){}
	/**
	 * 将1,2,3形式的字符串转为集合
	 * @param list
	 * @param source
	 * @return
	 */
	public static List<String> stringToList(List<String> list,String source){
		if(source!=null&&source.length()>0){
			String[] strArr = source.split(SEPARATOR);
			for (int i = 0; i < strArr.length; i++) {
				list.add(strArr[i]);
			}
		}
		return list;
	}
	/**
	 * 将[1,2,3]形式的字符串转为1,2,3形式的字符串
	 * @return
	 */
	public static String arrayStringTailor(String targetStr){
		String newStr = "";
		if(targetStr!=null&&targetStr.length()>0){
			if(targetStr.startsWith(TAILOR_LEFT_BRACKET)&&targetStr.endsWith(TAILOR_RIGHT_BRACKET)){
				newStr = targetStr.substring(1,targetStr.length());
			}
		}
		return newStr;
	}
	/**
	 * 将1,2,3形式的字符串转为[1,2,3]
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String stringSliptArrayToString(String str){
		return new StringBuilder().append(TAILOR_LEFT_BRACKET).append(str).append(TAILOR_RIGHT_BRACKET).toString();
	}
}
