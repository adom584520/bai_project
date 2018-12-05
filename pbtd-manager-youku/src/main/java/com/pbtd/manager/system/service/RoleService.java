package com.pbtd.manager.system.service;

import java.util.List;

import com.pbtd.manager.system.domain.Role;
import com.pbtd.manager.system.page.RoleQueryObject;
import com.pbtd.manager.util.PageResult;

public interface RoleService {
	/**
	 * 分页加查询
	 * @param qo
	 * @return
	 */
	PageResult queryList(RoleQueryObject qo);

	/**
	 * 查询所有的角色及其子菜单
	 * @return
	 */
	List<Role> queryAll();
	
	/**
	 * 查询账号缺少的角色
	 * @return
	 */
	List<Role> listRoleLack(Long loginInfoId);
	
	/**
	 * 根据loginInfoId查询role，不包括菜单
	 * @param loginInfoId
	 * @return
	 */
	List<Role> queryRoleByLoginInfoId(Long loginInfoId);

	void insert(Role role);

	void update(Role role);

	void delete(Long id);
	
	/**
	 * 根据loginInfoId删除
	 * @param loginInfoId
	 */
	void deleteLogAndRoleByLogId(Long loginInfoId);
	
	/**
	 * loginInfo和role的连接表添加数据
	 * @param loginInfoId
	 * @param roleId
	 */
	void inserLoginInfoAndRole(Long loginInfoId,Long roleId);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(List<Long> ids);
}
