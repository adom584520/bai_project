package com.pbtd.manager.live.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.live.domain.LiveBussChnCodePackage;
import com.pbtd.manager.live.domain.LiveChannel;

public interface LiveBussChnCodePackageMapper {
	
	List<LiveBussChnCodePackage> selectByPrimaryKey(LiveBussChnCodePackage record);

	/**
	 * 模糊统计符合查询条件的记录总数
	 *
	 * @param queryParams 查询参数
	 * @return 记录总数
	 */
	int count(Map<String, Object> queryParams);

	/**
	 * 模糊获取符合查询条件的分页记录
	 *
	 * @param start 记录起始位置
	 * @param limit 记录条数
	 * @param queryParams 查询参数
	 * @return 记录列表
	 */
	List<LiveBussChnCodePackage> page(int start, int limit, Map<String, Object> queryParams);

	/**
	 * 精确获取符合查询条件的记录
	 *
	 * @param queryParams 查询参数
	 * @return 记录列表
	 */
	List<LiveBussChnCodePackage> find(Map<String, Object> queryParams);
	List<LiveChannel> finds(Map<String, Object> queryParams);
	int counts(Map<String, Object> queryParams);
	/**
	 * 根据标识获取记录
	 *
	 * @param id 标识
	 * @return 记录
	 */
	LiveBussChnCodePackage load(int id);

	/**
	 * 删除多条
	 * 
	 * @param ids 标识列表
	 * @return 被删除的记录数
	 */
	int deletes(Map<String, Object> ids);
	/**
	 * 新增多条
	 * 
	 * @param ids 标识列表
	 * @return 被删除的记录数
	 */
	int creates(Map<String, Object> ids);

	/**
	 * 插入记录
	 *
	 * @param vodBussChnCodePackage VodBussChnCodePackage实例
	 * @return 被插入的记录标识
	 */
	int insert(LiveBussChnCodePackage record);
	/**
	 * 修改记录
	 *
	 * @param vodBussChnCodePackage VodBussChnCodePackage实例
	 * @return 被修改的记录数
	 */
	int update(LiveBussChnCodePackage record);


	int getMaxBussNum(int bussid);
}