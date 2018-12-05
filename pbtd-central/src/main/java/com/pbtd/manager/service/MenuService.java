package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.Menu;

public interface MenuService {
	/**
	 * 查询所有的菜单，包括父菜单中的所有子菜单
	 * @return
	 */
	List<Menu> queryAll();
}
