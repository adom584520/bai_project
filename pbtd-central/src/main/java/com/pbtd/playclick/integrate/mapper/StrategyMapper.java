package com.pbtd.playclick.integrate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.integrate.domain.Strategy;
@Mapper
public interface StrategyMapper {
	 /**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

    /**
     * 模糊获取符合查询条件的分页记录
     */
    List<Strategy> page(int start, int limit, Map<String, Object> queryParams);

    /**
     * 精确获取符合查询条件的记录
     */
    List<Strategy> find(Map<String, Object> queryParams);

    /**
     * 精确生成将要插入的记录的序号
     */
    int generatePosition(Map<String, Object> queryParams);

    /**
     * 根据标识获取记录
     */
    Strategy load(int id);

    /**
     * 插入记录
     */
    int insert(Strategy Strategy);

    /**
     * 修改记录
     */
    int update(Strategy Strategy);

	/**
     * 删除多条
     */
    int deletes(Map<String, Object> ids);
    List<Map<String,Object>> findvodmapping(Map<String, Object> queryParams);
	//查询爬取偏移值
    Map<String,Object>  getoffset(Map<String,Object> params);
	int updateoffset(Map<String,Object> params);
}
