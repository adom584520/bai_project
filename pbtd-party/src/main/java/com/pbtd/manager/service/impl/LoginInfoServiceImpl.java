package com.pbtd.manager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.manager.domain.Menu;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.mapper.LoginInfoMapper;
import com.pbtd.manager.service.LoginInfoService;
import com.pbtd.manager.service.MenuService;
import com.pbtd.manager.util.AesEncryptUtil;
import com.pbtd.manager.util.LoginInfoConstant;
import com.pbtd.manager.util.LoginInfoContext;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {
	@Autowired
	private LoginInfoMapper loginInfoMapper;
	@Autowired
	private MenuService menuService;

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
		if (loginInfo.getLevel() == LoginInfoConstant.ADMIN_SYSTEM_MANAGER) {
			List<Menu> menuAll = menuService.queryAll();
			LoginInfoContext.setSelfMenu(menuAll);
			return loginInfo;
		}
		// 党建只有超级管理权限的账号就可以,普通账号没有菜单显示
		List<Menu> allMenus = new ArrayList<>();
		LoginInfoContext.setSelfMenu(allMenus);
		return loginInfo;
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

	@Override
	public void updateAdmin() {
		loginInfoMapper.updateAdmin(LoginInfoConstant.ADMIN_LOGININFO_NAME,
				LoginInfoConstant.encryption(LoginInfoConstant.ADMIN_LOGININFO_PASSWORD));
	}
}
