package com.pbtd.manager.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.domain.Menu;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.service.MenuService;
import com.pbtd.manager.util.LoginInfoContext;
import com.pbtd.manager.util.ResultBean;

/**
 * 菜单操作
 * 
 * @author JOJO
 *
 */
@Controller
@RequestMapping("menu")
public class MenuController {
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	@Autowired
	private MenuService menuService;

	/**
	 * 角色页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("page")
	public String page() {
		return "/pb/menu";
	}

	/**
	 * 根据ID查询菜单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("get")
	@ResponseBody
	public ResultBean<Menu> getMenu(Long id) {
		ResultBean<Menu> resultBean = new ResultBean<Menu>();
		Menu menu = null;
		try {
			menu = menuService.getMenu(id);
			resultBean.setData(menu);
			resultBean.setMessage("查询成功！");
		} catch (Exception e) {
			resultBean.setSuccess(false);
			resultBean.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-菜单管理-菜单查询失败！", e);
		}
		return resultBean;
	}

	/**
	 * 查询所有的根菜单，不包括子菜单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("root")
	@ResponseBody
	public List<Menu> rootMenu() {
		List<Menu> menuRoot = menuService.menuRoot();
		return menuRoot;
	}

	/**
	 * 删除菜单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public ResultBean<Menu> delete(Long id) {
		ResultBean<Menu> resultBean = new ResultBean<>();
		try {
			menuService.deleteMenu(id);
			resultBean.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			resultBean.setSuccess(false);
			resultBean.setMessage(e.getMessage());
			logger.warn("系统管理-菜单管理-删除菜单-" + e.getMessage(), e);
		} catch (Exception e) {
			resultBean.setSuccess(false);
			resultBean.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-菜单管理-删除菜单", e);
		}
		return resultBean;
	}

	/**
	 * 获取所有菜单
	 * 
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public List<Menu> list() {
		List<Menu> data = null;
		try {
			data = menuService.queryList();
		} catch (Exception e) {
			data = new ArrayList<>();
			logger.error("系统管理-菜单管理-获取所有菜单失败！", e);
		}
		return data;
	}

	/**
	 * 根据角色id获取菜单的id
	 * 
	 * @return
	 */
	@RequestMapping("by_role_id")
	@ResponseBody
	public List<Long> menuByRoleId(Long roleId) {
		List<Long> data = menuService.queryMenuIdByRoleId(roleId);
		return data;
	}

	/**
	 * 获取可访问的菜单
	 * 
	 * @return
	 */
	@RequestMapping("self")
	@ResponseBody
	public List<Menu> menuSelf() {
		List<Menu> selfMenu = LoginInfoContext.getSelfMenu();
		return selfMenu;
	}

	/**
	 * 新增菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping("insert")
	@ResponseBody
	public ResultBean<Menu> insert(Menu menu) {
		ResultBean<Menu> resultBean = new ResultBean<Menu>();
		try {
			menuService.insertMenu(menu);
			resultBean.setMessage("添加成功！");
			resultBean.setSuccess(true);
		} catch (JsonMessageException e) {
			resultBean.setMessage(e.getMessage());
			resultBean.setSuccess(false);
			logger.warn("系统管理-菜单管理-新增菜单-" + e.getMessage(), e);
		} catch (Exception e) {
			resultBean.setMessage("系统出错，请联系管理员！");
			resultBean.setSuccess(false);
			logger.error("系统管理-菜单管理-新增菜单", e);
		}
		return resultBean;
	}

	/**
	 * 编辑菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public ResultBean<Menu> update(Menu menu) {
		ResultBean<Menu> resultBean = new ResultBean<Menu>();
		try {
			menuService.updateMenu(menu);
			resultBean.setMessage("修改成功！");
			resultBean.setSuccess(true);
		} catch (JsonMessageException e) {
			logger.warn("系统管理-菜单管理-编辑菜单-" + e.getMessage(), e);
			resultBean.setMessage(e.getMessage());
			resultBean.setSuccess(false);
		} catch (Exception e) {
			logger.error("系统管理-菜单管理-编辑菜单", e);
			resultBean.setMessage("系统出错，请联系管理员！");
			resultBean.setSuccess(false);
		}
		return resultBean;
	}

	/**
	 * 根据父菜单Id查询所有的子菜单
	 * 
	 * @param prentId
	 * @return
	 */
	@RequestMapping("query_menu_by_parent_id")
	@ResponseBody
	public List<Menu> queryMenuByparentIdMenu(Long parentId) {
		return menuService.queryMenuByparentIdMenu(parentId);
	}
}