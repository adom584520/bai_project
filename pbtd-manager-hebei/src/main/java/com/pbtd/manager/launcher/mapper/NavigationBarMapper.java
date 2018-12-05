package com.pbtd.manager.launcher.mapper;

import java.util.List;

import com.pbtd.manager.launcher.domain.NavigationBar;
import com.pbtd.manager.launcher.page.NavigationBarQueryObject;

public interface NavigationBarMapper {
	/**
	 * 根据查询条件查询数据
	 * @param qo
	 * @return
	 */
	List<NavigationBar> queryList(NavigationBarQueryObject qo);
	
	/**
	 * 根据查询条件查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(NavigationBarQueryObject qo);

	/**
	 * 添加NavigationBar
	 * @param NavigationBar
	 */
	Integer insert(NavigationBar navigationBar);

	/**
	 * 删除navigationBar
	 * @param id
	 */
	Integer delete(Long id);

	/**
	 * 修改navigationBar
	 * @param id
	 */
	Integer update(NavigationBar navigationBar);

	/**
	 * 根据id获取navigationBar
	 * @param id
	 * @return
	 */
	NavigationBar navigationBarById(Long id);
}
