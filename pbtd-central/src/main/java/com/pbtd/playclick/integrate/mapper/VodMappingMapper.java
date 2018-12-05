package com.pbtd.playclick.integrate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.integrate.domain.VodMapping;
@Mapper
public interface VodMappingMapper {
    int count(Map<String, Object> queryParams);
    List<VodMapping> find(Map<String, Object> queryParams);
    VodMapping load(int id);
    int insert(VodMapping vodmapping);
    int update(VodMapping vodmapping);
    int deletes(Map<String, Object> ids);
}
