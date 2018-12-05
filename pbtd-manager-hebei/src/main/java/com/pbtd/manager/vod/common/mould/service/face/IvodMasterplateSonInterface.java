package com.pbtd.manager.vod.common.mould.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.common.mould.domain.vodMasterplateSon;

public interface IvodMasterplateSonInterface {
    
    int delete(Map<String, Object> queryParams);
    int insert(vodMasterplateSon m);
    vodMasterplateSon load(Integer id);
    int update(vodMasterplateSon m);
    List<vodMasterplateSon> find(Map<String, Object> queryParams);
    List<vodMasterplateSon> page(Map<String, Object> queryParams);
    int count(Map<String, Object> queryParams);
}