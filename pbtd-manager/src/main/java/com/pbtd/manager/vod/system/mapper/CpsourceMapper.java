package com.pbtd.manager.vod.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.system.domain.Cpsource;
@Mapper
public interface CpsourceMapper {
	  
    int count(Map<String, Object> queryParams);
    List<Cpsource> find(Map<String, Object> queryParams);
    Cpsource load(int id);
    int insert(Cpsource cpsource);
    int update(Cpsource cpsource);
    int deletes(Map<String, Object> queryParams);
}
