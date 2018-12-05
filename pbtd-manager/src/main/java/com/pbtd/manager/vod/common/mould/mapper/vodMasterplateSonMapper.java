package com.pbtd.manager.vod.common.mould.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.common.mould.domain.vodMasterplateSon;
@Mapper
public interface vodMasterplateSonMapper {
    int delete(Map<String, Object> queryParams);
    int insert(vodMasterplateSon m);
    vodMasterplateSon load(Integer id);
    int update(vodMasterplateSon m);
    List<vodMasterplateSon> find(Map<String, Object> queryParams);
    List<vodMasterplateSon> page(Map<String, Object> queryParams);
    List<vodMasterplateSon> findSon(Map<String, Object> queryParams);
    int count(Map<String, Object> queryParams);
    List<vodMasterplateSon>  selectByPrimaryKey(Integer id);

}