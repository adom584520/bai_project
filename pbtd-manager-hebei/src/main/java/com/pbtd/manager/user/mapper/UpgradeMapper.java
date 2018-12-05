package com.pbtd.manager.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.user.domain.Upgrade;
import com.pbtd.manager.user.page.UpgradeQueryObject;

public interface UpgradeMapper {

	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(UpgradeQueryObject qo);

	/**
	 * 高级查询加分页-查询数据集合
	 * @param qo
	 * @return
	 */
	List<Upgrade> queryList(UpgradeQueryObject qo);

	/**
	 * 增加Upgrade
	 * @param Upgrade
	 * @return
	 */
	Integer insert(Upgrade upgrade);

	/**
	 * 修改Upgrade
	 * @param Upgrade
	 * @return
	 */
	Integer update(Upgrade upgrade);

	/**
	 * 删除Upgrade
	 * @param id
	 * @return
	 */
	Integer delete(Long id);

	/**
	 * 根据类型查询是否有升级信息
	 * @param type
	 * @return
	 */
	Upgrade queryByType(@Param("type")Integer type,@Param("status")Integer status);

	/**
	 * 根据类型修改升级信息状态
	 * @param type
	 */
	void updateByType(@Param("type")Integer type,@Param("status")Integer status);
}
