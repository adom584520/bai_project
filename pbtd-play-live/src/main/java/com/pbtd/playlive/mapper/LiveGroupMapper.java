package com.pbtd.playlive.mapper;
	
import java.util.List;
import java.util.Map;

import com.pbtd.playlive.domain.LiveGroup;

public interface LiveGroupMapper {
	
	List<LiveGroup> selectByPrimaryKey(LiveGroup record);

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
	List<LiveGroup> page(int start, int limit, Map<String, Object> queryParams);

	/**
	 * 精确获取符合查询条件的记录
	 *
	 * @param queryParams 查询参数
	 * @return 记录列表
	 */
	List<LiveGroup> find(Map<String, Object> queryParams);
	/**
	 * 根据标识获取记录
	 *
	 * @param id 标识
	 * @return 记录
	 */
	LiveGroup load(int id);
	
	void truncateTable();
	int insert(LiveGroup record);

}