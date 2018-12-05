package com.pbtd.manager.system.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.system.domain.SysParam;

public interface SysParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysParam record);

    int insertSelective(SysParam record);

    SysParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysParam record);

    int updateByPrimaryKey(SysParam record);
    
    List<SysParam> select(Map<String, Object> queryParams);
    
    SysParam load(int id);
	 List<Map<String,Object>> findonline(Map<String,Object> queryParams);
	 List<Map<String,Object>> find(Map<String,Object> queryParams);
     int count(Map<String,Object> queryParams);
     int insert(Map<String,Object> queryParams);
     int update(Map<String,Object> queryParams);
     int delete(Map<String,Object> queryParams);

}