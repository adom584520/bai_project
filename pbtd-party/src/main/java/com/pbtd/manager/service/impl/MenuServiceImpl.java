package com.pbtd.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.manager.domain.Menu;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.mapper.MenuMapper;
import com.pbtd.manager.query.MenuQueryObject;
import com.pbtd.manager.service.MenuService;
import com.pbtd.manager.util.LoginInfoConstant;
import com.pbtd.manager.util.LoginInfoContext;
import com.pbtd.manager.util.SystemUtil;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuMapper menuMapper;

	public List<Menu> queryAll() {
		List<Menu> menus = menuMapper.queryRootMenu();
		return menus;
	}

	public List<Menu> queryByRoleId(Long roleId) {
		return menuMapper.queryMenuByRoleId(roleId);
	}

	public List<Long> queryMenuIdByRoleId(Long roleId) {
		return menuMapper.queryMenuIdByRoleId(roleId);
	}

	@Transactional
	public void deleteRoleAndMenuByRole(Long roleId) {
		menuMapper.deleteRoleAndMenuByRole(roleId);
	}

	@Transactional
	public void insertRoleAndMenu(Long roleId, Long menuId) {
		menuMapper.insertRoleAndMenu(roleId, menuId);
	}

	@Override
	public List<Menu> queryList() {
		MenuQueryObject qo = new MenuQueryObject();
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("登录信息失效，请重新登录！");
		}
		List<Menu> selfMenu = LoginInfoContext.getSelfMenu();
		// 将父子结构的菜单转为平级菜单结构
		List<Menu> newSelfMenu = new ArrayList<Menu>();
		SystemUtil.goodOrdinary(selfMenu, newSelfMenu);
		// 当账号是超级管理员，直接查询所有的角色信息，不是则查询该账号拥有的所有角色信息
		if (!(LoginInfoConstant.ADMIN_SYSTEM_MANAGER.equals(current.getLevel()))) {
			qo.setIds(newSelfMenu);
		}
		List<Menu> data = menuMapper.queryList(qo);
		// 将菜单的结构整理好
		SystemUtil.findMenuPrent(data);
		// 更新账号的菜单信息
		LoginInfoContext.setSelfMenu(data);
		return data;
	}

	@Override
	public Menu getMenu(Long id) {
		return menuMapper.getMenu(id);
	}

	@Transactional
	public void deleteMenu(Long id) {
		int row = menuMapper.deleteMenu(id);
		if (row == 0) {
			throw new JsonMessageException("该条数据不存在！");
		}
		menuMapper.deleteRoleAndMenuByMenuId(id);
	}

	@Override
	public List<Menu> menuRoot() {
		return menuMapper.menuRoot();
	}

	@Transactional
	public void insertMenu(Menu menu) {
		int row = menuMapper.insertMenu(menu);
		if (row == 0) {
			throw new JsonMessageException();
		}
	}

	@Transactional
	public void updateMenu(Menu menu) {
		int row = menuMapper.updateMenu(menu);
		if (row == 0) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Override
	public List<Menu> queryMenuByparentIdMenu(Long parentId) {
		return menuMapper.queryMenuByparentIdMenu(parentId);
	}
}
