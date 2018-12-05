package com.pbtd.manager.launcher.mapper;

import java.util.List;

import com.pbtd.manager.launcher.domain.LauncherGroup;
import com.pbtd.manager.launcher.page.LauncherGroupQueryObject;

public interface LauncherGroupMapper {
	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(LauncherGroupQueryObject qo);

	/**
	 * 高级查询加分页-查询数据集合
	 * @param qo
	 * @return
	 */
	List<LauncherGroup> queryList(LauncherGroupQueryObject qo);

	/**
	 * 增加mac
	 * @param launcherGroup
	 * @return
	 */
	Integer insert(LauncherGroup launcherGroup);

	/**
	 * 修改mac
	 * @param launcherGroup
	 * @return
	 */
	Integer update(LauncherGroup launcherGroup);

	/**
	 * 删除launcherGroup
	 * @param id
	 * @return
	 */
	Integer delete(Long id);

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
	 * @return
	 */
	Integer deleteBatch(List<Long> ids);
}
