package com.pbtd.playlive.mapper;
	
import java.util.List;
import java.util.Map;

import com.pbtd.playlive.domain.LiveVersion;

public interface LiveVersionMapper {
	LiveVersion selectByPrimaryKey(LiveVersion record);

	/**
	 * 模糊统计符合查询条件的记录总数
	 *
	 * @param queryParams 查询参数
	 * @return 记录总数
	 */
	int count(Map<String, Object> queryParams);

	/**
	 * 精确获取符合查询条件的记录
	 *
	 * @param queryParams 查询参数
	 * @return 记录列表
	 */
	List<LiveVersion> find(Map<String, Object> queryParams);
	/**
	 * 根据标识获取记录
	 *
	 * @param id 标识
	 * @return 记录
	 */
	LiveVersion load(int id);
	
	void truncateTable();
	int insert(LiveVersion record);


}