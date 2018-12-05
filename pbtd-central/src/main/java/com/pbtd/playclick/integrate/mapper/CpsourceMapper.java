package com.pbtd.playclick.integrate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.integrate.domain.Cpsource;
import com.pbtd.playclick.integrate.domain.Strategy;
@Mapper
public interface CpsourceMapper {
	  
    int count(Map<String, Object> queryParams);
    List<Strategy> find(Map<String, Object> queryParams);
    Cpsource load(int id);
    int insert(Cpsource cpsource);
    int update(Cpsource cpsource);
    int deletes(Map<String, Object> queryParams);
}
