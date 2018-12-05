package com.pbtd.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.domain.Menu;
import com.pbtd.manager.mapper.MenuMapper;
import com.pbtd.manager.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuMapper menuMapper;

	public List<Menu> queryAll() {
		List<Menu> menus = menuMapper.queryRootMenu();
		return menus;
	}
}
