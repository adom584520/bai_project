package com.pbtd.manager.system.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.user.domain.SysCountSeries;

public interface SysCountSeriesMapper {

    int insert(SysCountSeries record);
	int delete(Map<String,Object> queryParams);

	List<Map<String,Object>> findallchannel();
	
	int count(Map<String,Object> queryParams);
	List<Map<String,Object>> find(Map<String,Object> queryParams);

	
}