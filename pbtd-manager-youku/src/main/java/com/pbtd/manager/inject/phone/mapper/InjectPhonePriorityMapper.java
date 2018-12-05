package com.pbtd.manager.inject.phone.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.inject.phone.domain.InjectPriority;


public interface InjectPhonePriorityMapper  {
	
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
