package com.pbtd.manager.service;

import com.pbtd.manager.domain.LoginInfo;

public interface LoginInfoService {
	/**
	 * 登录查询
	 * @param username
	 * @param password
	 * @return
	 */
	LoginInfo login(String username,String password);
}
