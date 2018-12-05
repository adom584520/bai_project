package com.pbtd.manager.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.component.SystemValueInjectConstant;
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
import com.pbtd.manager.system.util.HttpClientUtil;
import com.pbtd.manager.system.util.LoginInfoConstant;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.system.web.controller.LoginInfoController;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInfoController.class);
	@Autowired
	private LoginInfoMapper loginInfoMapper;
	@Autowired
	private RoleService roleService;
	@Autowired
	private LoginInfoContext loginInfoContext;
	@Autowired
	private MenuService menuService;
	@Autowired
	private SystemValueInjectConstant systemValueInjectConstant;

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
	public void insert(final LoginInfo loginInfo) {
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
		new Thread() {
			public void run() {
				HashMap<String, String> params = new HashMap<>();
				params.put("username", loginInfo.getUsername());
				params.put("contactInformation", loginInfo.getContactInformation());
				params.put("createLoginInfo", loginInfo.getCreateLoginInfo());
				params.put("realName", loginInfo.getRealName());
				LOGGER.info("系统管理-账号管理-中心平台添加账号分发至分平台！");
				String returnInfo = null;
				try {
					returnInfo = HttpClientUtil.doPost(systemValueInjectConstant.insertLoginInfo, params,
							systemValueInjectConstant.charset);
					if (returnInfo == null) {
						throw new JsonMessageException(
								"中心平台添加账号分发分平台账号失败！" + "响应参数为null,接口：" + systemValueInjectConstant.insertLoginInfo);
					}
					ResultBean<Object> parseObject = JSON.parseObject(returnInfo, ResultBean.class);
					if (!(parseObject != null && parseObject.isSuccess())) {
						throw new JsonMessageException("中心平台分发分平台账号响应失败！" + parseObject.toString() + "接口："
								+ systemValueInjectConstant.insertLoginInfo);
					}
				} catch (Exception e) {
					LOGGER.error("系统管理-账号管理-中心平台添加账号分发分平台账号失败！", e);
				}
				LOGGER.info("系统管理-账号管理-中心平台添加账号分发分平台账号成功！" + returnInfo);
			}
		}.start();
	}

	@Transactional
	public void update(final LoginInfo loginInfo) {
		final LoginInfo newLoginInfo = loginInfoMapper.queryByUsername(loginInfo.getUsername());
		if (newLoginInfo != null) {
			if (!newLoginInfo.getId().equals(loginInfo.getId())) {
				throw new JsonMessageException("该账号名已存在！");
			}
		}
		Integer row = loginInfoMapper.update(loginInfo);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
		new Thread() {
			public void run() {
				HashMap<String, String> params = new HashMap<>();
				params.put("target", newLoginInfo.getUsername());
				params.put("username", loginInfo.getUsername());
				params.put("contactInformation", loginInfo.getContactInformation());
				params.put("realName", loginInfo.getRealName());
				LOGGER.info("系统管理-账号管理-中心平台修改账号分发至分平台！");
				String returnInfo = null;
				try {
					returnInfo = HttpClientUtil.doPost(systemValueInjectConstant.updateLoginInfo, params,
							systemValueInjectConstant.charset);
					if (returnInfo == null) {
						throw new JsonMessageException(
								"中心平台修改账号分发分平台账号失败！" + "响应参数为null,接口：" + systemValueInjectConstant.updateLoginInfo);
					}
					ResultBean<Object> parseObject = JSON.parseObject(returnInfo, ResultBean.class);
					if (!(parseObject != null && parseObject.isSuccess())) {
						throw new JsonMessageException("中心平台修改账号分发分平台账号响应失败！" + parseObject.toString() + "接口："
								+ systemValueInjectConstant.insertLoginInfo);
					}
				} catch (Exception e) {
					LOGGER.error("系统管理-账号管理-中心平台修改账号分发分平台账号失败！", e);
				}
				LOGGER.info("系统管理-账号管理-中心平台修改账号分发分平台账号成功！" + returnInfo);
			}
		}.start();
	}

	@Transactional
	public void delete(Long id) {
		final LoginInfo loginInfo = loginInfoMapper.queryById(id);
		if (loginInfo == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		Integer row = loginInfoMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
		new Thread() {
			public void run() {
				HashMap<String, String> params = new HashMap<>();
				params.put("username", loginInfo.getUsername());
				LOGGER.info("系统管理-账号管理-中心平台删除账号分发至分平台！");
				String returnInfo = null;
				try {
					returnInfo = HttpClientUtil.doPost(systemValueInjectConstant.deleteLoginInfo, params,
							systemValueInjectConstant.charset);
					if (returnInfo == null) {
						throw new JsonMessageException(
								"中心平台删除账号分发分平台账号失败！" + "响应参数为null,接口：" + systemValueInjectConstant.deleteLoginInfo);
					}
					ResultBean<Object> parseObject = JSON.parseObject(returnInfo, ResultBean.class);
					if (!(parseObject != null && parseObject.isSuccess())) {
						throw new JsonMessageException("中心平台删除账号分发分平台账号响应失败！" + parseObject.toString() + "接口："
								+ systemValueInjectConstant.insertLoginInfo);
					}
				} catch (Exception e) {
					LOGGER.error("系统管理-账号管理-中心平台删除账号分发分平台账号失败！", e);
				}
				LOGGER.info("系统管理-账号管理-中心平台删除账号分发分平台账号成功！" + returnInfo);
			}
		}.start();
	}

	@Transactional
	public void updateStatus(Long id, Integer status) {
		Integer row = loginInfoMapper.updateStatus(id, status);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
		final LoginInfo loginInfo = loginInfoMapper.queryById(id);
		new Thread() {
			public void run() {
				HashMap<String, String> params = new HashMap<>();
				params.put("username", loginInfo.getUsername());
				params.put("status", String.valueOf(loginInfo.getStatus()));
				LOGGER.info("系统管理-账号管理-中心平台修改账号状态分发至分平台！");
				String returnInfo = null;
				try {
					returnInfo = HttpClientUtil.doPost(systemValueInjectConstant.updateStatus, params,
							systemValueInjectConstant.charset);
					if (returnInfo == null) {
						throw new JsonMessageException(
								"中心平台修改账号状态分发分平台账号失败！" + "响应参数为null,接口：" + systemValueInjectConstant.updateStatus);
					}
					ResultBean<Object> parseObject = JSON.parseObject(returnInfo, ResultBean.class);
					if (!(parseObject != null && parseObject.isSuccess())) {
						throw new JsonMessageException("中心平台修改账号状态分发分平台账号响应失败！" + parseObject.toString() + "接口："
								+ systemValueInjectConstant.insertLoginInfo);
					}
				} catch (Exception e) {
					LOGGER.error("系统管理-账号管理-中心平台修改账号状态分发分平台账号失败！", e);
				}
				LOGGER.info("系统管理-账号管理-中心平台修改账号状态分发分平台账号成功！" + returnInfo);
			}
		}.start();
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
		final LoginInfo loginInfo = loginInfoMapper.queryById(loginInfoId);
		new Thread() {
			public void run() {
				HashMap<String, String> params = new HashMap<>();
				params.put("username", loginInfo.getUsername());
				LOGGER.info("系统管理-账号管理-中心平台重置账号密码分发至分平台！");
				String returnInfo = null;
				try {
					returnInfo = HttpClientUtil.doPost(systemValueInjectConstant.resetPassword, params,
							systemValueInjectConstant.charset);
					if (returnInfo == null) {
						throw new JsonMessageException(
								"中心平台重置账号密码分发分平台账号失败！" + "响应参数为null,接口：" + systemValueInjectConstant.resetPassword);
					}
					ResultBean<Object> parseObject = JSON.parseObject(returnInfo, ResultBean.class);
					if (!(parseObject != null && parseObject.isSuccess())) {
						throw new JsonMessageException("中心平台重置账号密码分发分平台账号响应失败！" + parseObject.toString() + "接口："
								+ systemValueInjectConstant.resetPassword);
					}
				} catch (Exception e) {
					LOGGER.error("系统管理-账号管理-中心平台重置账号密码分发分平台账号失败！", e);
				}
				LOGGER.info("系统管理-账号管理-中心平台重置账号密码分发分平台账号成功！" + returnInfo);
			}
		}.start();
	}

	@Override
	@Transactional
	public void updateSelfInfo(final LoginInfo loginInfo) {
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
		new Thread() {
			public void run() {
				HashMap<String, String> params = new HashMap<>();
				params.put("username", loginInfo.getUsername());
				params.put("contactInformation", loginInfo.getContactInformation());
				params.put("realName", loginInfo.getRealName());
				LOGGER.info("系统管理-账号管理-中心平台个人资料修改分发至分平台！");
				String returnInfo = null;
				try {
					returnInfo = HttpClientUtil.doPost(systemValueInjectConstant.updateSelfInfo, params,
							systemValueInjectConstant.charset);
					if (returnInfo == null) {
						throw new JsonMessageException(
								"中心平台个人资料修改分发分平台账号失败！" + "响应参数为null,接口：" + systemValueInjectConstant.updateSelfInfo);
					}
					ResultBean<Object> parseObject = JSON.parseObject(returnInfo, ResultBean.class);
					if (!(parseObject != null && parseObject.isSuccess())) {
						throw new JsonMessageException("中心平台个人资料修改分发分平台账号响应失败！" + parseObject.toString() + "接口："
								+ systemValueInjectConstant.resetPassword);
					}
				} catch (Exception e) {
					LOGGER.error("系统管理-账号管理-中心平台个人资料修改分发分平台账号失败！", e);
				}
				LOGGER.info("系统管理-账号管理-中心平台个人资料修改分发分平台账号成功！" + returnInfo);
			}
		}.start();
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
		final String username = loginInfo.getUsername();
		final String password = LoginInfoConstant.encryption(newPassword);
		new Thread() {
			public void run() {
				HashMap<String, String> params = new HashMap<>();
				params.put("username", username);
				params.put("password", password);
				LOGGER.info("系统管理-账号管理-中心平台个人密码修改分发至分平台！");
				String returnInfo = null;
				try {
					returnInfo = HttpClientUtil.doPost(systemValueInjectConstant.updatePassword, params,
							systemValueInjectConstant.charset);
					if (returnInfo == null) {
						throw new JsonMessageException(
								"中心平台个人密码修改分发分平台账号失败！" + "响应参数为null,接口：" + systemValueInjectConstant.updatePassword);
					}
					ResultBean<Object> parseObject = JSON.parseObject(returnInfo, ResultBean.class);
					if (!(parseObject != null && parseObject.isSuccess())) {
						throw new JsonMessageException("中心平台个人密码修改分发分平台账号响应失败！" + parseObject.toString() + "接口："
								+ systemValueInjectConstant.resetPassword);
					}
				} catch (Exception e) {
					LOGGER.error("系统管理-账号管理-中心平台个人密码修改分发分平台账号失败！", e);
				}
				LOGGER.info("系统管理-账号管理-中心平台个人密码修改分发分平台账号成功！" + returnInfo);
			}
		}.start();
	}

	@Transactional
	public void deleteBatch(List<Long> ids) {
		int row = loginInfoMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}
}
