package com.pbtd.manager.system.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.system.domain.SysParamList;

public interface SysParamListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysParamList record);

    int insertSelective(SysParamList record);

    SysParamList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysParamList record);

    int updateByPrimaryKey(SysParamList record);
    
	// List<Map<String,Object>> find(Map<String,Object> queryParams);
	SysParamList findone(Map<String,Object> queryParams);

}