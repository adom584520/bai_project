package com.pbtd.manager.mapper;

import java.util.List;

import com.pbtd.manager.domain.Group;
import com.pbtd.manager.query.GroupQueryObject;

public interface GroupMapper {
	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(GroupQueryObject qo);

	/**
	 * 高级查询加分页-查询数据集合
	 * @param qo
	 * @return
	 */
	List<Group> queryList(GroupQueryObject qo);

	/**
	 * 增加mac
	 * @param launcherGroup
	 * @return
	 */
	Integer insert(Group launcherGroup);

	/**
	 * 修改mac
	 * @param launcherGroup
	 * @return
	 */
	Integer update(Group launcherGroup);

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
	 * @return
	 */
	Integer deleteBatch(List<Long> ids);
}
