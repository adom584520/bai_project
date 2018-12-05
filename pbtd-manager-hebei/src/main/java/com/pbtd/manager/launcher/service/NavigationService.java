package com.pbtd.manager.launcher.service;

import java.util.List;

import com.pbtd.manager.launcher.domain.Navigation;
import com.pbtd.manager.launcher.page.NavigationQueryObject;
import com.pbtd.manager.util.PageResult;

public interface NavigationService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(NavigationQueryObject qo);

	/**
	 * 添加导航
	 * @param nav
	 */
	void insert(Navigation nav);

	/**
	 * 删除导航
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改导航
	 * @param id
	 */
	void update(Navigation nav);

	/**
	 * 上下线
	 * @param id
	 * @param status
	 */
	void uplineOrDownLine(Navigation nav);

	/**
	 * 根据分组id查询所有的导航
	 * @param groupId
	 * @return
	 */
	List<Navigation> queryListByGroupId(Long groupId);

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
	void updateDataStatus(List<Long> list,Integer dataStatus);
}
