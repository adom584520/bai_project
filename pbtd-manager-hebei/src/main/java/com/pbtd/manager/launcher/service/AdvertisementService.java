package com.pbtd.manager.launcher.service;

import java.util.List;

import com.pbtd.manager.launcher.domain.Advertisement;
import com.pbtd.manager.launcher.page.AdvertisementQueryObject;
import com.pbtd.manager.util.PageResult;

public interface AdvertisementService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(AdvertisementQueryObject qo);

	/**
	 * 添加adv
	 * @param adv
	 */
	void insert(Advertisement adv);

	/**
	 * 删除adv
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改adv
	 * @param id
	 */
	void update(Advertisement adv);

	/**
	 * 上下线操作，判断同分组同广告位是否有相同的已上线广告
	 * @param id
	 * @param groupId
	 * @param status
	 */
	void uplineOrDownLine(Long id,Integer status);

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
