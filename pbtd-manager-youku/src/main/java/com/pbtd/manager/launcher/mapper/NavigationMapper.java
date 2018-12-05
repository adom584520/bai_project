package com.pbtd.manager.launcher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.launcher.domain.Navigation;
import com.pbtd.manager.launcher.page.NavigationQueryObject;

public interface NavigationMapper {
	/**
	 * 根据查询条件查询数据
	 * @param qo
	 * @return
	 */
	List<Navigation> queryList(NavigationQueryObject qo);

	/**
	 * 根据查询条件查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(NavigationQueryObject qo);

	/**
	 * 添加导航
	 * @param nav
	 * @return
	 */
	Integer insert(Navigation nav);

	/**
	 * 删除导航
	 * @param id
	 * @return
	 */
	Integer delete(@Param("id")Long id,@Param("status")Integer status);

	/**
	 * 修改导航
	 * @param nav
	 * @return
	 */
	Integer update(Navigation nav);

	/**
	 * 根据id获取导航
	 * @param id
	 * @return
	 */
	Navigation navigationById(Long id);

	/**
	 * 上下线操作
	 * @param adv
	 * @return
	 */
	Integer uplineOrDownLine(Navigation nav);

	/**
	 * 根据分组ID和导航的顺序查询导航
	 * @param groupId
	 * @param sortpos
	 * @return
	 */
	Navigation queryByGroupIdSortpos(@Param("groupId")Long groupId,@Param("sortpos")Integer sortpos,@Param("status")Integer status);

	/**
	 * 根据id查询单条数据
	 * @param id
	 * @return
	 */
	Navigation queryById(Long id);

	/**
	 * 根据分组id查询所有的导航
	 * @param groupId
	 * @return
	 */
	List<Navigation> queryListByGroupId(Long groupId);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteBatch(List<Long> ids);
}
