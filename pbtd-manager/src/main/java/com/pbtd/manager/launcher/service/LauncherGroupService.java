package com.pbtd.manager.launcher.service;

import java.util.List;

import com.pbtd.manager.launcher.domain.LauncherGroup;
import com.pbtd.manager.launcher.page.LauncherGroupQueryObject;
import com.pbtd.manager.util.PageResult;

public interface LauncherGroupService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(LauncherGroupQueryObject qo);

	/**
	 * 添加launcherGroup
	 * @param mac
	 */
	void insert(LauncherGroup launcherGroup);

	/**
	 * 删除launcherGroup
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改launcherGroup
	 * @param id
	 */
	void update(LauncherGroup launcherGroup);

	/**
	 * 查询所有
	 * @return
	 */
	List<LauncherGroup> queryGroupListAll();

	/**
	 * 根据id查询分组
	 * @param id
	 * @return
	 */
	LauncherGroup queryById(Long id);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(List<Long> ids);
}
