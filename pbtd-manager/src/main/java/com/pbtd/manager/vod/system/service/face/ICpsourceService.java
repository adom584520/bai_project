package com.pbtd.manager.vod.system.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.system.domain.Cpsource;

public interface ICpsourceService {
	  
    int count(Map<String, Object> queryParams);
    List<Cpsource> find(Map<String, Object> queryParams);
    Cpsource load(int id);
    int insert(Cpsource cpsource);
    int update(Cpsource cpsource);
    int deletes(Map<String, Object> queryParams);
}
