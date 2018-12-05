package com.pbtd.manager.launcher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.launcher.domain.Advertisement;
import com.pbtd.manager.launcher.page.AdvertisementQueryObject;

public interface AdvertisementMapper {

	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(AdvertisementQueryObject qo);

	/**
	 * 高级查询加分页-查询数据集合
	 * @param qo
	 * @return
	 */
	List<Advertisement> queryList(AdvertisementQueryObject qo);

	/**
	 * 增加adv
	 * @param adv
	 * @return
	 */
	Integer insert(Advertisement adv);

	/**
	 * 修改adv
	 * @param adv
	 * @return
	 */
	Integer update(Advertisement adv);

	/**
	 * 删除adv
	 * @param id
	 * @return
	 */
	Integer delete(Long id);

	/**
	 * 根据分组和广告类型和上线状态查询广告
	 * @param groupId
	 * @param showType
	 * @return
	 */
	Advertisement queryByGroupIdAndType(@Param("groupId")Long groupId,@Param("type")Integer type,@Param("status")Integer status);

	/**
	 * 上下线操作
	 * @param adv
	 * @return
	 */
	Integer uplineOrDownLine(Advertisement adv);

	/**
	 * 根据id查询单条数据
	 * @param id
	 * @return
	 */
	Advertisement queryById(Long id);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteBatch(List<Long> ids);
	
	/**
	 * 根据分组ID修改数据是否有效
	 * @param groupId
	 * @param dataType
	 */
	void updateDataStatus(@Param("list")List<Long> list,@Param("dataStatus")Integer dataStatus);
}
