package com.pbtd.playuser.service;

import java.util.Map;

import com.pbtd.playuser.domain.UserBaseInfo;
import com.pbtd.playuser.util.JsonMessage;

public interface IUserBaseInfoService {

	//获取验证码
	public JsonMessage getcode(long mobile);
	//登录
	public JsonMessage login(String mobile,String code,String device);
	public JsonMessage firstlogin(String mobile,Integer flag);
	//校验token
	public JsonMessage checkToken(String Token,String UserId);
	//查询用户
	public UserBaseInfo selectUser(String userId);
	
	
	public JsonMessage insertPosition(Map<String, Object> queryParams);
	public JsonMessage insertsaveSearch(Map<String, Object> queryParams);
	public JsonMessage querystatistics0(Map<String, Object> queryParams); //所有频道
	public JsonMessage querystatistics1(Map<String, Object> queryParams);//当前频道的各个wholeid 点击次数
	public JsonMessage querystatistics2(Map<String, Object> queryParams);//当前频道当前模块具体运营位  点击次数
	public JsonMessage searchStatistics(Map<String, Object> queryParams);//搜索统计点击次数
	public JsonMessage otherstatistics(Map<String, Object> queryParams);//功能使用次数次数
	public JsonMessage getuserdata(Map<String, Object> queryParams);//获取用户数据
	public JsonMessage getdataforhour(Map<String, Object> queryParams);//获取每小时统计数据
	
}
