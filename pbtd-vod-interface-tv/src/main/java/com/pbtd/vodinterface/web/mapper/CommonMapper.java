package com.pbtd.vodinterface.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
	List<Map<String,Object>> getactors(Map<String,Object> queryParams);
	List<Map<String,Object>> getactorlist(Map<String,Object> queryParams);
	List<Map<String,Object>> getChannellist(Map<String,Object> queryParams);
	List<Map<String,Object>> getlable(Map<String,Object> queryParams);
	List<Map<String,Object>> getspecial(Map<String,Object> queryParams);
	List<Map<String,Object>> getspecialvideo(Map<String,Object> queryParams);
	List<Map<String,Object>> findChannel(Map<String,Object> queryParams);
	List<Map<String,Object>> getlabeltype(Map<String,Object> queryParams);
	
}
