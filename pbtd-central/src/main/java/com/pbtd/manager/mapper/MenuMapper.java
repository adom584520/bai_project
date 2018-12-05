package com.pbtd.manager.mapper;

import java.util.List;

import com.pbtd.manager.domain.Menu;

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
}
