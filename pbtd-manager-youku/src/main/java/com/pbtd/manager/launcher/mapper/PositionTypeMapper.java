package com.pbtd.manager.launcher.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.launcher.domain.PositionType;


public interface PositionTypeMapper {
	 /**
     * 插入记录
     */
    public int add(PositionType positionType);
 
	/**
     * 删除多条
     */
    public int deletes(Map<String, Object> ids);
    /**
     * 编辑
     */
    public int modify(PositionType positionType);
    /**
     * 精确获取符合查询条件的记录
     */
     public List<PositionType> find(Map<String, Object> queryParams);

    /**
     * 精确生成将要插入的记录的序号
     */
    int generatePosition(Map<String, Object> queryParams);

    /**
     * 根据标识获取记录
     */
    PositionType load(int id);
    

	/**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);
	
}
