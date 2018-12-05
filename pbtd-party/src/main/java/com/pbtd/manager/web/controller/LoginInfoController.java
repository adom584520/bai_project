package com.pbtd.manager.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.service.LoginInfoService;
import com.pbtd.manager.util.LoginInfoContext;
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

	@RequestMapping("/page")
	public String page(Model model) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			model.addAttribute("loginInfo", new LoginInfo());
		} else {
			current.setPassword(null);
			model.addAttribute("loginInfo", current);
		}
		return "/pb/loginInfo";
	}

	/**
	 * 修改当前账号信息
	 * 
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping("/self_info_update")
	@ResponseBody
	public ResultBean<LoginInfo> selfInfoEdit(LoginInfo loginInfo) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			loginInfoService.updateSelfInfo(loginInfo);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-个人资料编辑-" + e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-个人资料编辑-" + e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查询当前用户信息
	 * 
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping("/query_self")
	@ResponseBody
	public ResultBean<LoginInfo> qeurySelf() {
		ResultBean<LoginInfo> json = new ResultBean<>();
		try {
			LoginInfo current = LoginInfoContext.getCurrent();
			if (current != null) {
				json.setData(current);
				json.setMessage("查询成功！");
			} else {
				throw new JsonMessageException("登录信息失效，请重新登录！");
			}
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-查询当前用户信息-" + e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-个人资料编辑-" + e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping("/update_password")
	@ResponseBody
	public ResultBean<LoginInfo> updatePassword(String oldPassword, String newPassword) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		try {
			loginInfoService.updatePassword(oldPassword, newPassword);
			json.setMessage("修改成功！");
		} catch (JsonMessageException e) {
			json.setSuccess(false);
			json.setMessage(e.getMessage());
			logger.warn("系统管理-账号管理-修改密码-" + e.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-账号管理-个人资料编辑-" + e.getMessage(), e);
		}
		return json;
	}
}