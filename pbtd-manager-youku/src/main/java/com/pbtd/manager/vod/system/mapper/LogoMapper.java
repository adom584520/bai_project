package com.pbtd.manager.vod.system.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.system.domain.Logo;



public interface LogoMapper {
     List<Map<String,Object>> find(Map<String,Object> queryParams);
     Logo load(int id);
     int count(Map<String,Object> queryParams);
     int add(Map<String,Object> queryParams);
     int modify(Map<String,Object> queryParams);
     int delete(Map<String,Object>queryParams);
}
