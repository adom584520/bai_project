package com.pbtd.manager.vod.phone.common.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.phone.common.domain.VodLabeltype;

 
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
    //下发添加
    int insertjson(VodLabeltype VodLabeltype);
     //查询排序的最大值和最小值
	  Map<String, Object> findmaxVSminsequence(Map<String, Object> map);
	 //查询更改数据的历史排序
	  List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams);
	  //查询排序
	  List<Map<String, Object>> findsequence(Map<String, Object> map);

}
