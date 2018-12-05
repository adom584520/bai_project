package com.pbtd.manager.launcher.service;

import java.util.List;

import com.pbtd.manager.launcher.domain.LauncherUI;
import com.pbtd.manager.launcher.page.LauncherUIQueryObject;
import com.pbtd.manager.util.PageResult;

public interface LauncherUIService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(LauncherUIQueryObject qo);

	/**
	 * 根据id查询LauncherUI
	 * @param id
	 * @return
	 */
	LauncherUI queryById(Long id);

	/**
	 * 添加launcherGroupDetail
	 * @param mac
	 */
	void insert(LauncherUI launcherUI);

	/**
	 * 删除launcherGroupDetail
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改launcherGroup
	 * @param id
	 */
	void update(LauncherUI launcherUI);

	/**
	 * 根据groupId和状态码上下线
	 * @param id
	 * @param status
	 */
	void uplineOrDownline(Long id,Long groupId,Integer status);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(List<Long> ids);

	/**
	 * 根据分组ID修改数据是否有效
	 * @param groupId
	 * @param dataType
	 */
	void updateDataStatus(List<Long> list,Boolean dataType);
}
