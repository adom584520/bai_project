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
	 * 修改超级管理员账号
	 */
	void updateAdmin();

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

	/**
	 * 中心平台添加分平台账号
	 * @param loginInfo
	 */
	void insertExternal(LoginInfo loginInfo);

	/**
	 * 中心平台修改分平台账号
	 */
	void updateExternal(String target,LoginInfo loginInfo);

	/**
	 * 中心平台删除分平台账号
	 * @param username
	 */
	void deleteExternal(String username);

	/**
	 * 中心平台重置分平台账号密码
	 * @param username
	 */
	void resetPasswordExternal(String username);

	/**
	 * 中心平台修改分平台个人资料
	 * @param loginInfo
	 */
	void updateSelfInfoExternal(LoginInfo loginInfo);

	/**
	 * 中心平台修改分平台个人密码
	 * @param username
	 * @param password
	 */
	void updatePasswordExternal(String username,String password);

	/**
	 * 中心平台修改分平台账号状态
	 * @param username
	 * @param status
	 */
	void updateStatusExternal(String username,Integer status);
}
