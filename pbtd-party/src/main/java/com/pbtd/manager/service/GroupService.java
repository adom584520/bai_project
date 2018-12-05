package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.Group;
import com.pbtd.manager.query.GroupQueryObject;
import com.pbtd.manager.util.PageResult;

public interface GroupService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(GroupQueryObject qo);

	/**
	 * 添加launcherGroup
	 * @param mac
	 */
	void insert(Group launcherGroup);

	/**
	 * 删除launcherGroup
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改launcherGroup
	 * @param id
	 */
	void update(Group launcherGroup);

	/**
	 * 查询所有
	 * @return
	 */
	List<Group> queryGroupListAll();

	/**
	 * 根据id查询分组
	 * @param id
	 * @return
	 */
	Group queryById(Long id);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(List<Long> ids);

	/**
	 * 根据mac查询分组
	 * @param mac
	 * @return
	 */
	Group queryByMac(String mac);
}
