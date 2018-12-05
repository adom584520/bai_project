package com.pbtd.dto;

public class Constant {

	//查询接口  返回状态码
	public static final int LOCAL_QUERY_CODE_FAIL=0;// 失败
	
	public static final int LOCAL_QUERY_CODE_SUCCESS=1;// 成功
	
	//是否存在  包装字段
	public static final int LOCAL_QUERY_EXISTCODE_NO=0;//不存在
	
	public static final int LOCAL_QUERY_EXISTCODE_YES=1;//存在

	public static final int LOCAL_QUERY_EXISTCODE_OTHER=-1;//其他  未获取返回message
	
}
