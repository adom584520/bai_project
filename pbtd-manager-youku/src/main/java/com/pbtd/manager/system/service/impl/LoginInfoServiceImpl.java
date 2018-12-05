package com.pbtd.manager.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.domain.Menu;
import com.pbtd.manager.system.domain.Role;
import com.pbtd.manager.system.mapper.LoginInfoMapper;
import com.pbtd.manager.system.page.LoginInfoQueryObject;
import com.pbtd.manager.system.service.LoginInfoService;
import com.pbtd.manager.system.service.MenuService;
import com.pbtd.manager.system.service.RoleService;
import com.pbtd.manager.system.util.AesEncryptUtil;
import com.pbtd.manager.system.util.LoginInfoConstant;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {
	@Autowired
	private LoginInfoMapper loginInfoMapper;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private LoginInfoContext loginInfoContext;
	public LoginInfo login(String username, String password) throws Exception {
		if (username != null && "".equals(username)) {
			throw new JsonMessageException("用户名不能为空！");
		}
		if (password != null && "".equals(password)) {
			throw new JsonMessageException("密码不能为空！");
		}
		String desEncrypt = AesEncryptUtil.desEncrypt(password);
		LoginInfo loginInfo = loginInfoMapper.login(username, LoginInfoConstant.encryption(desEncrypt),
				LoginInfoConstant.NATURAL_STATUS);
		if (loginInfo == null) {
			throw new JsonMessageException("账号或密码错误！");
		}
		LoginInfoContext.setCurrent(loginInfo);
		loginInfoContext.setsession();
		if (loginInfo.getLevel() == LoginInfoConstant.ADMIN_SYSTEM_MANAGER) {
			List<Menu> menuAll = menuService.queryAll();
			List<Role> roleAll = roleService.queryAll();
			LoginInfoContext.setSelfRole(roleAll);
			LoginInfoContext.setSelfMenu(menuAll);
			return loginInfo;
		}
		// 查询出用户的权限和菜单
		List<Role> roles = roleService.queryRoleByLoginInfoId(loginInfo.getId());
		List<Menu> allMenus = new ArrayList<>();
		Set<String> allPermission = new HashSet<String>();
		for (int i = 0; i < roles.size(); i++) {
			List<Menu> roleMenu = menuService.queryByRoleId(roles.get(i).getId());
			for (int j = 0; j < roleMenu.size(); j++) {
				if (!allPermission.contains(roleMenu.get(j).getPermission())) {
					allMenus.add(roleMenu.get(j));
					allPermission.add(roleMenu.get(j).getPermission());
				}
			}
		}
		findMenuPrent(allMenus);
		LoginInfoContext.setSelfRole(roles);
		LoginInfoContext.setSelfMenu(allMenus);
		LoginInfoContext.setSelfPermission(allPermission);
		return loginInfo;
	}

	public PageResult queryLoginInfoList(LoginInfoQueryObject qo) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("登录信息失效，请重新登录！");
		}
		// 当账号是超级管理员，直接查询所有的角色信息，不是则查询该账号拥有的所有角色信息
		if (!(LoginInfoConstant.ADMIN_SYSTEM_MANAGER.equals(current.getLevel()))) {
			qo.setLevel(LoginInfoConstant.GENERAL_SYSTEM_MANAGER);
		}
		Long count = loginInfoMapper.queryCount(qo);
		if (count == 0) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<LoginInfo> data = loginInfoMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Transactional
	public void insert(LoginInfo loginInfo) {
		LoginInfo newLoginInfo = loginInfoMapper.queryByUsername(loginInfo.getUsername());
		if (newLoginInfo != null) {
			throw new JsonMessageException("该账号名已存在！");
		}
		LoginInfo current = LoginInfoContext.getCurrent();
		loginInfo.setCreateLoginInfo(current.getUsername());
		if (loginInfo.getContactInformation() == null & loginInfo.getContactInformation().length() < 1) {
			loginInfo.setContactInformation("");
		}
		loginInfo.setCreateDate(new Date());
		loginInfo.setLevel(LoginInfoConstant.GENERAL_SYSTEM_MANAGER);
		loginInfo.setStatus(LoginInfoConstant.DISABLED_STATUS);
		loginInfo.setPassword(LoginInfoConstant.encryption(LoginInfoConstant.INITIAL_PASSWORD));
		Integer row = loginInfoMapper.insert(loginInfo);
		if (row < 1) {
			throw new JsonMessageException("新增失败！");
		}
	}

	@Transactional
	public void update(LoginInfo loginInfo) {
		LoginInfo newLoginInfo = loginInfoMapper.queryByUsername(loginInfo.getUsername());
		if (newLoginInfo != null) {
			if (!newLoginInfo.getId().equals(loginInfo.getId())) {
				throw new JsonMessageException("该账号名已存在！");
			}
		}
		Integer row = loginInfoMapper.update(loginInfo);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Transactional
	public void delete(Long id) {
		Integer row = loginInfoMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}

	@Transactional
	public void updateStatus(Long id, Integer status) {
		Integer row = loginInfoMapper.updateStatus(id, status);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Transactional
	public void loginInfoAddRole(Long loginInfoId, List<Long> roles) {
		if (roles.size() < 1) {
			// 当传入的角色为空时，判断该账号是取消所有角色还是不添加角色
			List<Role> selfRoles = roleService.queryRoleByLoginInfoId(loginInfoId);
			if (selfRoles.size() < 1) {
				return;
			} else {
				roleService.deleteLogAndRoleByLogId(loginInfoId);
				return;
			}
		}
		roleService.deleteLogAndRoleByLogId(loginInfoId);
		for (int i = 0; i < roles.size(); i++) {
			roleService.inserLoginInfoAndRole(loginInfoId, roles.get(i));
		}
	}

	/**
	 * 将集合中的菜单对象按照父子对象映射的关系进行绑定
	 * 
	 * @param menus
	 */
	private void findMenuPrent(List<Menu> menus) {
		// 将子节点放入父节点
		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);
			Long prentId = menu.getParentId();
			if (prentId != null) {
				for (int j = 0; j < menus.size(); j++) {
					if (prentId == menus.get(j).getId()) {
						menus.get(j).getChildren().add(menu);
					}
				}
			}
		}
		// 将集合中不为根节点的节点删除
		Iterator<Menu> iterator = menus.iterator();
		while (iterator.hasNext()) {
			Menu menu = iterator.next();
			if (menu.getParentId() != null) {
				iterator.remove();
			}
		}
	}

	@Override
	public LoginInfo queryAdmin() {
		return loginInfoMapper.queryAdmin(LoginInfoConstant.ADMIN_LOGININFO_NAME);
	}

	@Override
	@Transactional
	public void insertAdmin() {
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setCreateDate(new Date());
		loginInfo.setCreateLoginInfo(LoginInfoConstant.ADMIN_LOGININFO_NAME);
		loginInfo.setContactInformation("");
		loginInfo.setLevel(LoginInfoConstant.ADMIN_SYSTEM_MANAGER);
		loginInfo.setRealName(LoginInfoConstant.ADMIN_LOGININFO_NAME);
		loginInfo.setStatus(LoginInfoConstant.NATURAL_STATUS);
		loginInfo.setUsername(LoginInfoConstant.ADMIN_LOGININFO_NAME);
		loginInfo.setPassword(LoginInfoConstant.encryption(LoginInfoConstant.ADMIN_LOGININFO_PASSWORD));
		loginInfoMapper.insertAdmin(loginInfo);
	}

	@Override
	@Transactional
	public void resetPassword(Long loginInfoId) {
		int row = loginInfoMapper.resetPassword(loginInfoId,
				LoginInfoConstant.encryption(LoginInfoConstant.INITIAL_PASSWORD));
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Override
	@Transactional
	public void updateSelfInfo(LoginInfo loginInfo) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("登录信息失效，请重新登录！");
		}
		LoginInfo newLoginInfo = loginInfoMapper.queryByUsername(loginInfo.getUsername());
		if (newLoginInfo != null) {
			if (!newLoginInfo.getId().equals(loginInfo.getId())) {
				throw new JsonMessageException("该账号名已存在！");
			}
		}
		loginInfo.setId(current.getId());
		int row = loginInfoMapper.updateSelfInfo(loginInfo);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Override
	@Transactional
	public void updatePassword(String oldPassword, String newPassword) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("登录信息失效，请重新登录！");
		}
		LoginInfo loginInfo = loginInfoMapper.queryByUsername(current.getUsername());
		if (!loginInfo.getPassword().equals(LoginInfoConstant.encryption(oldPassword))) {
			throw new JsonMessageException("旧密码输入错误！！");
		}
		int row = loginInfoMapper.resetPassword(current.getId(), LoginInfoConstant.encryption(newPassword));
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Transactional
	public void deleteBatch(List<Long> ids) {
		int row = loginInfoMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}

	@Override
	public void insertExternal(LoginInfo loginInfo) {
		loginInfo.setCreateDate(new Date());
		loginInfo.setPassword(LoginInfoConstant.encryption(LoginInfoConstant.INITIAL_PASSWORD));
		loginInfo.setStatus(LoginInfoConstant.DISABLED_STATUS);
		loginInfo.setLevel(LoginInfoConstant.GENERAL_SYSTEM_MANAGER);
		loginInfoMapper.insert(loginInfo);
	}

	@Override
	public void updateExternal(String target,LoginInfo loginInfo) {
		LoginInfo newLoginInfo = loginInfoMapper.queryByUsername(target);
		if (newLoginInfo == null) {
			return;
		}
		loginInfo.setId(newLoginInfo.getId());
		loginInfoMapper.update(loginInfo);
	}

	@Override
	public void deleteExternal(String username) {
		LoginInfo newLoginInfo = loginInfoMapper.queryByUsername(username);
		if (newLoginInfo == null) {
			return;
		}
		loginInfoMapper.delete(newLoginInfo.getId());
	}

	@Override
	public void resetPasswordExternal(String username) {
		LoginInfo newLoginInfo = loginInfoMapper.queryByUsername(username);
		if (newLoginInfo == null) {
			return;
		}
		loginInfoMapper.resetPassword(newLoginInfo.getId(),
				LoginInfoConstant.encryption(LoginInfoConstant.INITIAL_PASSWORD));
	}

	@Override
	public void updateSelfInfoExternal(LoginInfo loginInfo) {
		LoginInfo newLoginInfo = loginInfoMapper.queryByUsername(loginInfo.getUsername());
		if (newLoginInfo == null) {
			return;
		}
		loginInfo.setId(newLoginInfo.getId());
		loginInfoMapper.updateSelfInfo(loginInfo);
	}

	@Override
	public void updatePasswordExternal(String username, String password) {
		LoginInfo newLoginInfo = loginInfoMapper.queryByUsername(username);
		if (newLoginInfo == null) {
			return;
		}
		loginInfoMapper.resetPassword(newLoginInfo.getId(), password);
	}

	@Override
	public void updateStatusExternal(String username, Integer status) {
		LoginInfo newLoginInfo = loginInfoMapper.queryByUsername(username);
		if (newLoginInfo == null) {
			return;
		}
		loginInfoMapper.updateStatus(newLoginInfo.getId(), status);

	}
}
