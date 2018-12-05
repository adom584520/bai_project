package com.pbtd.playuser.service;

import com.pbtd.playuser.domain.UserBaseInfo;
import com.pbtd.playuser.util.JsonMessage;

public interface IUserBaseInfoService {

	//获取验证码
	public JsonMessage getcode(long mobile);
	//登录
	public JsonMessage login(String mobile,String code,String device);
	//校验token
	public JsonMessage checkToken(String Token,String UserId);
	//查询用户
	public UserBaseInfo selectUser(String userId);


	
}
