package com.pbtd.playlive.mapper;

import java.util.List;

import com.pbtd.playlive.domain.LiveTag;

public interface LiveTagMapper {

	List<LiveTag> selectByPrimaryKey(LiveTag record);

	/**
	 * 根据标识获取记录
	 *
	 * @param id 标识
	 * @return 记录
	 */
	LiveTag load(int id);

	void truncateTable();
	int insert(LiveTag record);

}