package com.pbtd.manager.system.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.user.domain.SysCountUser;

public interface SysCountUserMapper {
	int deleteByPrimaryKey(Map<String,Object> queryParams);

	int insert(SysCountUser record);


	SysCountUser selectByPrimaryKey(Integer id);
	
	SysCountUser selectforyestoday(Map<String,Object> queryParams);

	List<Map<String,Object>> find(Map<String,Object> queryParams);
	List<Map<String,String>> page(Map<String,Object> queryParams);
	
	int count(Map<String,Object> queryParams);

}