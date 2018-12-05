package com.pbtd.manager.system.service;

import java.util.List;

import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.page.LoginInfoQueryObject;
import com.pbtd.manager.util.PageResult;

public interface LoginInfoService {
	/**
	 * 登录查询
	 * @param username
	 * @param password
	 * @return
	 */
	LoginInfo login(String username,String password) throws Exception;

	/**
	 * 后台列表查询
	 * @param qo
	 * @return
	 */
	PageResult queryLoginInfoList(LoginInfoQueryObject qo);

	void insert(LoginInfo loginInfo);

	void update(LoginInfo loginInfo);

	void delete(Long id);
	
	void deleteBatch(List<Long> ids);

	/**
	 * 修改账号状态
	 * @param id
	 * @param status
	 */
	void updateStatus(Long id,Integer status);

	/**
	 * 给账号添加角色
	 * @param loginInfoId
	 * @param roles
	 */
	void loginInfoAddRole(Long loginInfoId,List<Long> roles);
	
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
	 * 重置密码
	 * @param loginInfoId
	 */
	void resetPassword(Long loginInfoId);
	
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
