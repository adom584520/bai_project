package com.pbtd.manager.system.mapper;

import java.util.List;
import java.util.Map;

public interface SysCountChannelMapper {
	
	int count(Map<String,Object> queryParams);
	List<Map<String,Object>> find(Map<String,Object> queryParams);
}