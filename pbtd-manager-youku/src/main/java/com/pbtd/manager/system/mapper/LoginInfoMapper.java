package com.pbtd.manager.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.page.LoginInfoQueryObject;

public interface LoginInfoMapper {
	/**
	 * 登录查询
	 * @param username
	 * @param password
	 * @param status  登录账号处于正常状态
	 * @return
	 */
	LoginInfo login(@Param("username")String username,@Param("password")String password,@Param("status")Integer status);

	/**
	 * 高级查询：总数量
	 * @param qo
	 * @return
	 */
	Long queryCount(LoginInfoQueryObject qo);

	/**
	 * 高级查询：总数据
	 * @param qo
	 * @return
	 */
	List<LoginInfo> queryList(LoginInfoQueryObject qo);

	Integer insert(LoginInfo loginInfo);

	Integer update(LoginInfo loginInfo);

	Integer delete(Long id);
	
	Integer deleteBatch(List<Long> ids);

	/**
	 * 禁用或激活账号
	 * @param id
	 * @param status
	 * @return
	 */
	Integer updateStatus(@Param("id")Long id,@Param("status")Integer status);

	/**
	 * 根据username查询LoginInfo
	 * @param username
	 * @return
	 */
	LoginInfo queryByUsername(String username);

	/**
	 * 查询默认的超级管理员账号
	 * @return
	 */
	LoginInfo queryAdmin(String username);

	/**
	 * 创建默认的超级管理员账号
	 * @return
	 */
	void insertAdmin(LoginInfo loginInfo);

	/**
	 * 重置账号密码
	 * @param id
	 * @param password
	 * @return
	 */
	int resetPassword(@Param("id")Long id,@Param("password")String password);
	
	/**
	 * 修改个人账号信息
	 * @param loginInfo
	 * @return
	 */
	int updateSelfInfo(LoginInfo loginInfo);
}
