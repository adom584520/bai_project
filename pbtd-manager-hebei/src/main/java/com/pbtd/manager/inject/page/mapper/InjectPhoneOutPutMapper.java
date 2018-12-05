package com.pbtd.manager.inject.page.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface InjectPhoneOutPutMapper {
	
	int countHwAlbumsinfovideo(Map<String, Object> queryParams);
	List<Map<String,Object>> findHwAlbumsinfovideo(Map<String, Object> queryParams);
	int countZxAlbumsinfovideo(Map<String, Object> queryParams);
	List<Map<String,Object>> findZxAlbumsinfovideo(Map<String, Object> queryParams);
	void updatePhoneInject(Map<String, Object> queryParams);
	String selectHwVersion(@Param("id")Integer id);
	String selectZxVersion(@Param("id")Integer id);
	
}
