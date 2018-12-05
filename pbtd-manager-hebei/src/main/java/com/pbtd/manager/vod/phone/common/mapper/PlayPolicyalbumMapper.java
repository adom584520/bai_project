package com.pbtd.manager.vod.phone.common.mapper;

import java.util.List;
import java.util.Map;

public interface PlayPolicyalbumMapper {
	/**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

	/**
     * 模糊获取符合查询条件的分页记录
     * @return 记录列表
     */
    List<Map<String, Object>> page(Map<String, Object> queryParams);

	/**
     * 根据标识获取记录
     */
    Map<String, Object> load(int id);

	/**
     * 插入记录
     */
    int insert(Map<String, Object> m);

	/**
     * 修改记录
     */
    int update(Map<String, Object> m);
    
	/**
     * 删除多条
     */
    int deletes(Map<String, Object> ids);
    
    int updateyoukuisshow(Map<String, Object> queryParams);

}
