package com.pbtd.playclick.integrate.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.playclick.integrate.domain.VodLabeltype;


 
public interface VodLabeltypeMapper {

	/**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

	/**
     * 模糊获取符合查询条件的分页记录
     * @return 记录列表
     */
    List<VodLabeltype> page(Map<String, Object> queryParams);

	/**
     * 根据标识获取记录
     */
    VodLabeltype load(int id);

	/**
     * 插入记录
     */
    int insert(VodLabeltype VodLabeltype);

	/**
     * 修改记录
     */
    int update(VodLabeltype VodLabeltype);
    
	/**
     * 删除多条
     */
    int deletes(Map<String, Object> ids);
}
