package com.pbtd.manager.system.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.domain.Menu;
import com.pbtd.manager.system.domain.Role;
import com.pbtd.manager.system.mapper.RoleMapper;
import com.pbtd.manager.system.page.RoleQueryObject;
import com.pbtd.manager.system.service.MenuService;
import com.pbtd.manager.system.service.RoleService;
import com.pbtd.manager.system.util.LoginInfoConstant;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private MenuService menuService;

	@Override
	public PageResult queryList(RoleQueryObject qo) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("登录信息失效，请重新登录！");
		}
		// 当账号是超级管理员，直接查询所有的角色信息，不是则查询该账号拥有的所有角色信息
		if (!(LoginInfoConstant.ADMIN_SYSTEM_MANAGER.equals(current.getLevel()))) {
			qo.setIds(LoginInfoContext.getSelfRole());
		}
		Long count = roleMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<Role> data = roleMapper.queryList(qo);
		LoginInfoContext.setSelfRole(data);
		return new PageResult(count, data);
	}

	@Override
	public List<Role> queryAll() {
		List<Role> roles = roleMapper.queryAll();
		findMenuPrent(roles);
		return roles;
	}

	/**
	 * 找爸爸
	 * 
	 * @param role
	 * @return
	 */
	private void findMenuPrent(List<Role> roles) {
		for (int i = 0; i < roles.size(); i++) {
			Role role = roles.get(i);
			List<Menu> menus = role.getMenus();
			for (int j = 0; j < menus.size(); j++) {
				Menu menu = menus.get(j);
				Long prentId = menu.getParentId();
				if (prentId != null) {
					for (int k = 0; k < menus.size(); k++) {
						if (prentId.equals(menus.get(k).getId())) {
							menu.getChildren().add(menu);
							break;
						}
					}
				}
			}
			Iterator<Menu> iterator = menus.iterator();
			while (iterator.hasNext()) {
				Menu menu = iterator.next();
				if (menu.getParentId() != null) {
					iterator.remove();
				}
			}
		}
	}

	@Transactional
	public void insert(Role role) {
		role.setCreateDate(new Date());
		Integer row = roleMapper.insert(role);
		if (row < 1) {
			throw new JsonMessageException("新增失败！");
		}
		List<Menu> menus = role.getMenus();
		for (int i = 0; i < menus.size(); i++) {
			menuService.insertRoleAndMenu(role.getId(), menus.get(i).getId());
		}
	}

	@Transactional
	public void update(Role role) {
		Integer row = roleMapper.update(role);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
		List<Menu> menus = role.getMenus();
		menuService.deleteRoleAndMenuByRole(role.getId());
		for (int i = 0; i < menus.size(); i++) {
			menuService.insertRoleAndMenu(role.getId(), menus.get(i).getId());
		}
	}

	@Transactional
	public void delete(Long id) {
		Integer row = roleMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
		menuService.deleteRoleAndMenuByRole(id);
	}

	@Override
	public List<Role> listRoleLack(Long loginInfoId) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("登录信息失效，请重新登录！");
		}
		List<Role> roleAll = null;
		if (LoginInfoConstant.ADMIN_SYSTEM_MANAGER.equals(current.getLevel())) {
			roleAll = roleMapper.queryAll();
		}else{
			roleAll = LoginInfoContext.getSelfRole();
		}
		if (roleAll.size() < 1) {
			return Collections.emptyList();
		}
		List<Role> selfRole = roleMapper.queryRoleByLoginInfoId(loginInfoId);
		if (selfRole.size() < 1) {
			return roleAll;
		}
		Iterator<Role> iterator = roleAll.iterator();
		while (iterator.hasNext()) {
			Long id = iterator.next().getId();
			for (int i = 0; i < selfRole.size(); i++) {
				if (id == selfRole.get(i).getId()) {
					iterator.remove();
					break;
				}
			}
		}
		return roleAll;
	}

	@Override
	public List<Role> queryRoleByLoginInfoId(Long loginInfoId) {
		return roleMapper.queryRoleByLoginInfoId(loginInfoId);
	}

	@Transactional
	public void deleteLogAndRoleByLogId(Long loginInfoId) {
		roleMapper.deleteLogAndRoleByLogId(loginInfoId);
	}

	@Transactional
	public void inserLoginInfoAndRole(Long loginInfoId, Long roleId) {
		roleMapper.inserLoginInfoAndRole(loginInfoId, roleId);
	}

	@Transactional
	public void deleteBatch(List<Long> ids) {
		int row = roleMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}
}
