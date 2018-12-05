package com.pbtd.manager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.service.LoginInfoService;
import com.pbtd.playclick.util.JsonMessage;

/**
 * 账号操作
 * 
 * @author JOJO
 *
 */
@Controller
public class LoginInfoController {
	@Autowired
	private LoginInfoService loginInfoService;

	/**
	 * 账号登录
	 * 
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public JsonMessage login(String username, String password, Model model) {
		JsonMessage json = new JsonMessage();
		LoginInfo loginInfo = null;
		try {
			loginInfo = loginInfoService.login(username, password);
			model.addAttribute("loginInfo", loginInfo);
		} catch (JsonMessageException r) {
			json.setSuccess(false);
			json.setMessage(r.getMessage());
			r.printStackTrace();
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMessage("系统正在维护中,请稍后！");
			e.printStackTrace();
		}
		return json;
	}
	@RequestMapping("loginInfo_page")
	public String loginInfoPage() {
		return "system/loginInfo";
	}
}