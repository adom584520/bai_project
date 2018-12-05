package com.pbtd.manager.web.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

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
 * 登录页面操作
 * 
 * @author JOJO
 *
 */
@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private LoginInfoService loginInfoService;

	/**
	 * 映射访问登录界面
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String redirectLogin() {
		return "login";
	}

	/**
	 * 账号登录
	 * 
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ResultBean<LoginInfo> login(String username, String password, Model model) {
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		LoginInfo loginInfo = null;
		try {
			loginInfo = loginInfoService.login(username, password);
			model.addAttribute("loginInfo", loginInfo);
			json.setMessage("登录成功");
			logger.info("系统管理-登录操作-账号名：" + username + "成功登录！");
		} catch (JsonMessageException r) {
			json.setSuccess(false);
			json.setMessage(r.getMessage());
			logger.warn("系统管理-登录操作-账号名：" + username + "登录失败！" + "错误信息：" + r.getMessage());
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统出错，请联系管理员！");
			logger.error("系统管理-登录操作-账号名：" + username + "登录失败！" + "错误信息：" + e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 注销登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/log_out")
	public String logOut(HttpServletRequest request) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			return "/login";

		}
		request.setAttribute("username", current.getUsername());
		Enumeration<String> em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		logger.info("系统管理-注销操作-账号名：" + current.getUsername());
		ResultBean<LoginInfo> json = new ResultBean<LoginInfo>();
		json.setSuccess(true);
		json.setMessage("注销成功！");
		return "/login";
	}
}