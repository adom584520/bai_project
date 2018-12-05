package com.pbtd.vodinterface.web.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.vodinterface.web.domain.SysParam;

public interface SysParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysParam record);

    int insertSelective(SysParam record);

    SysParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysParam record);

    int updateByPrimaryKey(SysParam record);
 
    List<SysParam> select(Map<String, Object> queryParams);
}