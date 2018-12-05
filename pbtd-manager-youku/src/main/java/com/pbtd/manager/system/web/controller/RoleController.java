package com.pbtd.manager.system.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.system.domain.Role;
import com.pbtd.manager.system.page.RoleQueryObject;
import com.pbtd.manager.system.service.RoleService;
import com.pbtd.manager.system.util.PermissionAnnotation;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

/**
 * 菜单操作
 * 
 * @author JOJO
 *
 */
@Controller
@RequestMapping("role")
public class RoleController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private RoleService roleService;

	@RequestMapping("page")
	@PermissionAnnotation(value = "/role/page")
	public String roelPage() {
		return "/system/role";
	}

	/**
	 * 分页加查询
	 * 
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public PageResult list(RoleQueryObject qo) {
		PageResult data = null;
		try {
			data = roleService.queryList(qo);
		} catch (Exception e) {
			logger.error("系统管理-角色管理-角色列表查询失败！",e);
		}
		return data;
	}

	/**
	 * 查询所有的角色及其子菜单
	 * 
	 * @return
	 */
	@RequestMapping("all")
	@ResponseBody
	public List<Role> roleAll() {
		List<Role> data = null;
		try {
			data = roleService.queryAll();
		} catch (Exception e) {
			logger.error("系统管理-角色管理-查询所有的角色及其子菜单！",e);
		}
		return data;
	}

	/**
	 * 查询账号未拥有的角色
	 * 
	 * @return
	 */
	@RequestMapping("lack")
	@ResponseBody
	public List<Role> roleLack(Long loginInfoId) {
		List<Role> data = null;
		try {
			data = roleService.listRoleLack(loginInfoId);
		} catch (Exception e) {
			logger.error("系统管理-角色管理-查询账号未拥有的角色",e);
		}
		return data;
	}

	/**
	 * 根据账号Id查询拥有的角色
	 * 
	 * @param loginInfoId
	 * @return
	 */
	@RequestMapping("/query_role_byL_logininfo_id")
	@ResponseBody
	public List<Role> queryRoleByLoginInfoId(Long loginInfoId) {
		List<Role> data = null;
		try {
			data = roleService.queryRoleByLoginInfoId(loginInfoId);
		} catch (Exception e) {
			logger.error("系统管理-角色管理-查询账号已拥有的角色",e);
		}
		return data;
	}

	/**
	 * 新增角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping("insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-新增角色")
	public ResultBean<Role> insert(Role role) {
		ResultBean<Role> json = new ResultBean<>();
		try {
			roleService.insert(role);
			json.setMessage("保存成功！");
		} catch (RuntimeException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-角色管理-新增角色-"+e.getMessage(),e);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-角色管理-新增角色",e);
		}
		return json;
	}

	/**
	 * 编辑角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-编辑角色")
	public ResultBean<Role> update(Role role) {
		ResultBean<Role> json = new ResultBean<>();
		try {
			roleService.update(role);
			json.setMessage("修改成功！");
		} catch (RuntimeException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-角色管理-编辑角色-"+e.getMessage(),e);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-角色管理-新增角色",e);
		}
		return json;
	}

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-删除角色")
	public ResultBean<Role> delete(Long id) {
		ResultBean<Role> json = new ResultBean<>();
		try {
			roleService.delete(id);
			json.setMessage("删除成功！");
		} catch (RuntimeException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-角色管理-删除角色-"+e.getMessage(),e);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-角色管理-删除角色",e);
		}
		return json;
	}
	@RequestMapping("delete_batch")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-批量删除角色")
	public ResultBean<Role> deleteBatch(String ids) {
		ResultBean<Role> json = new ResultBean<>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			roleService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (RuntimeException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-角色管理-批量删除角色-"+e.getMessage(),e);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-角色管理-批量删除角色",e);
		}
		return json;
	}
}