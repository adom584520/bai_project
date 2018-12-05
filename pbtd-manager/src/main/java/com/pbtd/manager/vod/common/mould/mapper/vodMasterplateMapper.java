package com.pbtd.manager.vod.common.mould.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.common.mould.domain.vodMasterplate;

@Mapper
public interface vodMasterplateMapper {


    int delete(Map<String, Object> queryParams);
    int insert(vodMasterplate m);
    vodMasterplate load(Integer id);
    int update(vodMasterplate m);
    List<vodMasterplate> find(Map<String, Object> queryParams);
    List<vodMasterplate> page(Map<String, Object> queryParams);
    int count(Map<String, Object> queryParams);
    int updateimg(Map<String, Object> queryParams);
}