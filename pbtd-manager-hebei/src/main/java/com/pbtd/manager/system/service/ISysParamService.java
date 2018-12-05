package com.pbtd.manager.system.service;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.system.domain.RealmName;
import com.pbtd.manager.system.domain.SysParam;

public interface ISysParamService {
	Map<String,Object> getSysParam(Map<String,Object> queryParams);
	
	 List<Map<String,Object>> find(Map<String,Object> queryParams);
     int count(Map<String,Object> queryParams);
     
	 SysParam load(int id);
     int insert(Map<String,Object> queryParams);
     int update(Map<String,Object> queryParams);
     int delete(Map<String,Object> queryParams);
	
}
