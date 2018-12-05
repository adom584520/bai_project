package com.pbtd.manager.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.system.domain.Role;
import com.pbtd.manager.system.page.RoleQueryObject;

/**
 * 角色和菜单中间表由menu管理
 * 
 * @author JOJO
 *
 */
public interface RoleMapper {
	/**
	 * 查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(RoleQueryObject qo);

	/**
	 * 分页加查询
	 * @param qo
	 * @return
	 */
	List<Role> queryList(RoleQueryObject qo);

	/**
	 * 查询所有角色信息
	 * @return
	 */
	List<Role> queryAll();

	/**
	 * 查询账号未拥有的角色
	 * @return
	 */
	List<Role> queryAllSimp();

	/**
	 * 根据loginInfoId查询role，不包括菜单
	 * @param loginInfoId
	 * @return
	 */
	List<Role> queryRoleByLoginInfoId(Long loginInfoId);

	Integer insert(Role role);

	Integer update(Role role);

	Integer delete(Long id);
	
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
	void inserLoginInfoAndRole(@Param("loginInfoId")Long loginInfoId,@Param("roleId")Long roleId);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteBatch(List<Long> ids);
}
