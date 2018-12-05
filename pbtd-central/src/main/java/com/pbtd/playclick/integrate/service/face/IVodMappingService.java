package com.pbtd.playclick.integrate.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.playclick.integrate.domain.VodMapping;


public interface IVodMappingService {
    int count(Map<String, Object> queryParams);
    List<VodMapping> find(Map<String, Object> queryParams);
    VodMapping load(int id);
    int insert(VodMapping vodmapping);
    int update(VodMapping vodmapping);
    int deletes(Map<String, Object> ids);
}
