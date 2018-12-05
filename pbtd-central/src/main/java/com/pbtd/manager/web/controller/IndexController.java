package com.pbtd.manager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页
 * 
 * @author JOJO
 *
 */
@Controller
public class IndexController {
	/**
	 * 跳转到主页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model) {
		return "index";
	}
}