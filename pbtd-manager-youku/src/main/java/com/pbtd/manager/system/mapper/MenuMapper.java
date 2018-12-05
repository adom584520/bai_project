package com.pbtd.manager.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.system.domain.Menu;
import com.pbtd.manager.system.page.MenuQueryObject;

/**
 * 角色和菜单中间表由menu管理
 * 
 * @author JOJO
 *
 */
public interface MenuMapper {
	/**
	 * 查询所有的顶级菜单
	 * @return
	 */
	List<Menu> queryRootMenu();

	/**
	 * 根据父菜单查询子菜单
	 * @param prentId
	 * @return
	 */
	List<Menu> queryMenuById(Long prentId);

	/**
	 * 通过roleid查询menu
	 * @param roleId
	 * @return
	 */
	List<Menu> queryMenuByRoleId(Long roleId);

	/**
	 * 根据roleid获取中间表所有菜单的id
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
	void insertRoleAndMenu(@Param("roleId")Long roleId,@Param("menuId")Long menuId);

	/**
	 * 查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(MenuQueryObject qo);

	/**
	 * 分页加查询
	 * @param qo
	 * @return
	 */
	List<Menu> queryList(MenuQueryObject qo);
	
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
	Integer deleteMenu(Long id);
	
	/**
	 * 查询所有的根菜单，不包括子菜单
	 * @return
	 */
	List<Menu> menuRoot();
	
	Integer insertMenu(Menu menu);
	
	/**
	 * 根据menuId做级联删除
	 * @param menuId
	 */
	void deleteRoleAndMenuByMenuId(Long menuId);
	
	Integer updateMenu(Menu menu);
	
	/**
	 * 根据父菜单Id查询所有的子菜单
	 * @param parent
	 * @return
	 */
	List<Menu> queryMenuByparentIdMenu(Long parentId);
}
