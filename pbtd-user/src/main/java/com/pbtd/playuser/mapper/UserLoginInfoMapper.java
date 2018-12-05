package com.pbtd.playuser.mapper;

import java.util.HashMap;

import com.pbtd.playuser.domain.UserLoginInfo;

public interface UserLoginInfoMapper {
	UserLoginInfo selectByPrimaryKey(String userid);

	UserLoginInfo selectusrerdevice(HashMap<?,?> tempBean);
	
	int updateToken(HashMap<?, ?> tempBean);
	
	int insertSelective(HashMap<?,?> tempBean);

	
}