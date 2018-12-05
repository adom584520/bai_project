package com.pbtd.manager.inject.tv.mapper;

import java.util.List;
import java.util.Map;

public interface InjectTvPriorityMapper  {
	
    int count(Map<String,Object> map);
    
    List<Map<String,Object>> find(Map<String,Object> map);
    
    List<Map<String,Object>> findChannels();
    
    List<Map<String,Object>>  findPartChannels();
    
    List<Map<String,Object>> findAllChannels();
    
    int savePriority(Map<String,Object> map);
    
    Map<String,Object> findById(Map<String,Object> map);
    
    int deletePriority(Map<String,Object> map);
    
    int updatePriority(Map<String,Object> map);
    
}
