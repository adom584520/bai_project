package com.pbtd.manager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录页面操作
 * 
 * @author JOJO
 *
 */
@Controller
public class LoginController {
	/**
	 * 映射访问登录界面
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String redirectLogin() {
		return "login";
	}
}