package com.pbtd.manager.vod.phone.hotseries.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.phone.hotseries.domain.VodHotseries;

public interface VodHotseriesMapper {
	//执播
	List<Map<String,Object>> find(Map<String,Object> queryParams);
	VodHotseries load(int id);
	int count(Map<String,Object> queryParams);
	int add(Map<String,Object> queryParams);
	int modify(Map<String,Object> queryParams);
	int updateStatus(Map<String,Object> queryParams);
	int deletes(Map<String,Object> queryParams);
	//绑定专辑
	List<Map<String,Object>> pagealbum(Map<String,Object> queryParams);
	int countalbum(Map<String,Object> queryParams);
	int addalbuminfo(Map<String,Object> queryParams);
	int updatesequence(Map<String,Object> queryParams);
	int deletealbuminfo(Map<String,Object> queryParams);
	//保存下发数据
	int insertjson(Map<String,Object> queryParams);
    //查询绑定信息的最大和最小排序
    Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map);
    //查询更改数据的历史排序
    List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams);
    //查询绑定信息排序
    List<Map<String, Object>> findalbumsequence(Map<String, Object> map);
}
