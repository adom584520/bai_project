package com.pbtd.manager.vod.tv.mould.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.tv.mould.domain.VodTvModule;
@Mapper
public interface VodTvModuleMapper {
	  
    int delete(Integer id);
    int deletemodule(Map<String, Object> queryParams);
    List<VodTvModule> select (Map<String,Object> queryParams);
    VodTvModule selectbymoduleid (Map<String,Object> queryParams);

    int insert(VodTvModule m);
    VodTvModule load(Map<String, Object> queryParams);
    int update(VodTvModule m);
    List<Map<String, Object>> find(Map<String, Object> queryParams);
    int findMaxModuleSeq(Map<String,Object> param);
    List<VodTvModule> page(Map<String, Object> queryParams);
    int count(Map<String, Object> queryParams);
    
    
    List<Map<String, Object>> pagealbum(Map<String, Object> queryParams);
    int countalbum(Map<String, Object> queryParams);
    int insertalbum(Map<String, Object> queryParams);
    int deletealbuminfo(Map<String, Object> queryParams);
    
    int updateimg(Map<String, Object> queryParams);
    /**
     * 更新module状态
    */
    int updateflag1(Map<String, Object> queryParams);
    /**
     * 更新channel状态
     */
    int updateflag2(Map<String, Object> queryParams);
    int updateflag3(Map<String, Object> queryParams);
    int updateshangxian(Map<String, Object> queryParams);
    
    int updateMoudleSeq(Map<String,Object> params);

}