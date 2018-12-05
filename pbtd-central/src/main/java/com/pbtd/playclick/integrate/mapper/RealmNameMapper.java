package com.pbtd.playclick.integrate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.integrate.domain.RealmName;


@Mapper
public interface RealmNameMapper {
	 List<Map<String,Object>> find(Map<String,Object> queryParams);
     RealmName load(int id);
     int count(Map<String,Object> queryParams);
     int insert(Map<String,Object> queryParams);
     int update(Map<String,Object> queryParams);
     int delete(Map<String,Object>queryParams);
     Map<String,Object>  findtitle(Map<String,Object> queryParams);
}
