package com.pbtd.manager.mapper;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.domain.LoginInfo;

public interface LoginInfoMapper {
	/**
	 * 登录查询
	 * @param username
	 * @param password
	 * @param status  登录账号处于正常状态
	 * @return
	 */
	LoginInfo login(@Param("username")String username,@Param("password")String password);
}
