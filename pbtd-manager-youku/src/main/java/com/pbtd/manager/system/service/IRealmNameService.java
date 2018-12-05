package com.pbtd.manager.system.service;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.system.domain.RealmName;



public interface IRealmNameService {
  		 List<Map<String,Object>> find(Map<String,Object> queryParams);
  		 RealmName load(int id);
	     int count(Map<String,Object> queryParams);
	     int insert(Map<String,Object> queryParams);
	     int update(Map<String,Object> queryParams);
	     int delete(Map<String,Object>queryParams);
		Map<String, Object> findtitle(Map<String,Object>queryParams);
}
