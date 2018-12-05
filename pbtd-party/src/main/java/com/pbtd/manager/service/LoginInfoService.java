package com.pbtd.manager.service;

import com.pbtd.manager.domain.LoginInfo;

public interface LoginInfoService {
	/**
	 * 登录查询
	 * @param username
	 * @param password
	 * @return
	 */
	LoginInfo login(String username,String password) throws Exception;

	/**
	 * 查询默认的超级管理员账号
	 * @return
	 */
	LoginInfo queryAdmin();

	/**
	 * 创建默认的超级管理员账号
	 * @return
	 */
	void insertAdmin();

	/**
	 * 修改超级管理员账号
	 */
	void updateAdmin();

	/**
	 * 修改个人账号信息
	 */
	void updateSelfInfo(LoginInfo loginInfo);

	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 */
	void updatePassword(String oldPassword,String newPassword);
}
