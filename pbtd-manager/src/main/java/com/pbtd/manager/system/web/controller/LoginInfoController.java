package com.pbtd.manager.system.web.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.page.LoginInfoQueryObject;
import com.pbtd.manager.system.service.LoginInfoService;
import com.pbtd.manager.system.util.LoggerUtil;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.system.util.PermissionAnnotation;
import com.pbtd.manager.util.CollectionVO;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.util.ResultBean;

/**
 * 账号操作
 * 
 * @author JOJO
 *
 */
@Controller
@RequestMapping("/loginInfo")
public class LoginInfoController {
	private static final Logger logger = LoggerFactory.getLogger(LoginInfoController.class);

	@Autowired
	private LoginInfoService loginInfoService;
	@Autowired
	private Environment environment;

	@PermissionAnnotation(value = "/loginInfo/page")
	@RequestMapping("/page")
	public String page(Model model) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if(current==null){
			model.addAttribute("loginInfo", new LoginInfo());
		}else{
			current.setPassword(null);
			model.addAttribute("loginInfo", current);
		}
		return "system/loginInfo";
	}

	/**
	 * 账号列表加高级查询
	 * 
	 * @param qo
	 * @return
	 * 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(LoginInfoQueryObject qo) {
		PageResult pageResult = null;
		try {
			pageResult = loginInfoService.queryLoginInfoList(qo);
		} catch (Exception e) {
			logger.error("系统管理-账号管理-查询失败",e);
		}
		return pageResult;
	}

	@RequestMapping("/insert")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-新增账号")
	public ResultBean<LoginInfo> insert(LoginInfo loginInfo) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			loginInfoService.insert(loginInfo);
			json.setMessage("添加成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-新增操作-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-新增操作-"+e.getMessage(),e);
		}
		return json;
	}

	@RequestMapping("/update")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-编辑账号")
	public ResultBean<LoginInfo> update(LoginInfo loginInfo) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			loginInfoService.update(loginInfo);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-编辑操作-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-编辑操作-"+e.getMessage(),e);
		}
		return json;
	}

	@RequestMapping("/delete")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-删除账号")
	public ResultBean<LoginInfo> delete(Long id) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			loginInfoService.delete(id);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-删除操作-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-删除操作-"+e.getMessage(),e);
		}
		return json;
	}
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete_batch")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-批量删除账号")
	public ResultBean<LoginInfo> deleteBatch(String ids) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			List<Long> list = JSON.parseArray(ids, Long.class);
			loginInfoService.deleteBatch(list);
			json.setMessage("删除成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-批量删除操作-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-批量删除操作-"+e.getMessage(),e);
		}
		return json;
	}

	@RequestMapping("/update_status")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-修改账号状态")
	public ResultBean<LoginInfo> updateStatus(Long id, Integer status) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			loginInfoService.updateStatus(id, status);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-编辑状态操作-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-编辑状态操作-"+e.getMessage(),e);
		}
		LoggerUtil.setInfoAndReturnData("系统管理-账号管理-编辑状态操作-"+json.getMessage(), json);
		return json;
	}

	@RequestMapping("/insert_role")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-分配账号权限")
	public ResultBean<LoginInfo> insertRole(Long loginInfoId, CollectionVO<Long> vo) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			loginInfoService.loginInfoAddRole(loginInfoId, vo.getList());
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-分配权限操作-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-分配权限操作-"+e.getMessage(),e);
		}
		LoggerUtil.setInfoAndReturnData("系统管理-账号管理-分配权限操作-"+json.getMessage(), json);
		return json;
	}
	/**
	 * 重置密码
	 * @param loginInfoId
	 * @return
	 */
	@RequestMapping("/reset_password")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-重置账号密码")
	public ResultBean<LoginInfo> resetPassword(Long loginInfoId) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			loginInfoService.resetPassword(loginInfoId);
			json.setMessage("重置成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-重置密码操作-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-重置密码操作-"+e.getMessage(),e);
		}
		LoggerUtil.setInfoAndReturnData("系统管理-账号管理-重置密码操作-"+json.getMessage(), json);
		return json;
	}
	
	/**
	 * 修改当前账号信息
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping("/self_info_update")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-修改个人账号信息")
	public ResultBean<LoginInfo> selfInfoEdit(LoginInfo loginInfo) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			loginInfoService.updateSelfInfo(loginInfo);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-个人资料编辑-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-个人资料编辑-"+e.getMessage(),e);
		}
		LoggerUtil.setInfoAndReturnData("系统管理-账号管理-个人资料编辑-"+json.getMessage(), json);
		return json;
	}
	/**
	 * 查询当前用户信息
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping("/query_self")
	@ResponseBody
	public ResultBean<LoginInfo> qeurySelf() {
		ResultBean<LoginInfo> json = new ResultBean<>();
		try {
			LoginInfo current = LoginInfoContext.getCurrent();
			if(current!=null){
				json.setData(current);
				json.setMessage("查询成功！");
			}else{
				throw new JsonMessageException("登录信息失效，请重新登录！");
			}
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-查询当前用户信息-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-个人资料编辑-"+e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping("/update_password")
	@ResponseBody
	@LogAnnotation(operationInfo = "系统管理-修改个人账号密码")
	public ResultBean<LoginInfo> updatePassword(String oldPassword,String newPassword) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			loginInfoService.updatePassword(oldPassword,newPassword);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-修改密码-"+e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-个人资料编辑-"+e.getMessage(),e);
		}
		return json;
	}
}