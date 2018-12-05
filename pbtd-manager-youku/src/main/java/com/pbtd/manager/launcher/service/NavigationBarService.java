package com.pbtd.manager.launcher.service;

import com.pbtd.manager.launcher.domain.NavigationBar;
import com.pbtd.manager.launcher.page.NavigationBarQueryObject;
import com.pbtd.manager.util.PageResult;

public interface NavigationBarService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(NavigationBarQueryObject qo);

	/**
	 * 添加NavigationBar
	 * @param NavigationBar
	 */
	int insert(NavigationBar navigationBar);

	/**
	 * 删除navigationBar
	 * @param id
	 */
	int delete(Long id);

	/**
	 * 修改navigationBar
	 * @param id
	 */
	int update(NavigationBar navigationBar);
	
	/**
	 * 根据id获取navigationBar
	 * @param id
	 * @return
	 */
	NavigationBar navigationBarById(Long id);
}
