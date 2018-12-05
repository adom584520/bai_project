package com.pbtd.manager.system.service;

import java.util.List;

import com.pbtd.manager.system.domain.Menu;

public interface MenuService {
	/**
	 * 查询所有的菜单，包括父菜单中的所有子菜单
	 * @return
	 */
	List<Menu> queryAll();

	/**
	 * 通过roleid查询menu
	 * @param roleId
	 * @return
	 */
	List<Menu> queryByRoleId(Long roleId);

	/**
	 * 根据roleid获取所有菜单的id
	 * @param roleId
	 * @return
	 */
	List<Long> queryMenuIdByRoleId(Long roleId);

	/**
	 * 根据角色id删除中间表中的关联数据
	 * @param roleId
	 */
	void deleteRoleAndMenuByRole(Long roleId);

	/**
	 * role和menu连接表添加数据
	 * @param roleId
	 * @param menuId
	 */
	void insertRoleAndMenu(Long roleId,Long menuId);

	/**
	  * 高级查询，菜单没有分页
	 * @param qo
	 * @return
	 */
	List<Menu> queryList();

	/**
	 * 根据id获取menu
	 * @param id
	 * @return
	 */
	Menu getMenu(Long id);

	/**
	 * 根据id删除menu
	 * @param id
	 */
	void deleteMenu(Long id);

	/**
	 * 查询出所有的根菜单,不包含子菜单
	 * @return
	 */
	List<Menu> menuRoot();

	void insertMenu(Menu menu);

	void updateMenu(Menu menu);

	/**
	 * 根据父菜单Id查询所有的子菜单
	 * @param parent
	 * @return
	 */
	List<Menu> queryMenuByparentIdMenu(Long parentId);
}
