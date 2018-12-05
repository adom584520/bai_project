package com.pbtd.manager.vod.system.mapper;

import java.util.Map;

import com.pbtd.manager.vod.system.domain.Cpsource;

public interface CpsourceMapper {
     public int insert(Cpsource cpsource);
     public int count(Map<String,Object> map);
}
