package com.pbtd.manager.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.domain.Menu;
import com.pbtd.manager.service.MenuService;

/**
 * 菜单操作
 * 
 * @author JOJO
 *
 */
@Controller
public class MenuController {
	@Autowired
	private MenuService menuService;

	/**
	 * 角色页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("menu_page")
	public String roelPage() {
		return "/system/menu";
	}

	/**
	 * 获取所有菜单
	 * 
	 * @return
	 */
	@RequestMapping("/menu_list")
	@ResponseBody
	public List<Menu> menuList() {
		List<Menu> data = null;
		try {
			data = menuService.queryAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}