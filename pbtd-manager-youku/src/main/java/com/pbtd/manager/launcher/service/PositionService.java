package com.pbtd.manager.launcher.service;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.launcher.domain.Position;

public interface PositionService {
	 /**
     * 插入记录
     */
    public int add(Position position);
 
	/**
     * 删除多条
     */
    public int deletes(Map<String, Object> ids);
    /**
     * 编辑
     */
    public int modify(Position position);
    /**
     * 精确获取符合查询条件的记录
     */
     public List<Position> find(Map<String, Object> queryParams);

    /**
     * 精确生成将要插入的记录的序号
     */
    int generatePosition(Map<String, Object> queryParams);

    /**
     * 根据标识获取记录
     */
    Position load(int id);
    

	/**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);
    
    /**
     * 获取运营类型
     */
    List <Map<String,Object>> queryType(Map<String,Object> params);
}
