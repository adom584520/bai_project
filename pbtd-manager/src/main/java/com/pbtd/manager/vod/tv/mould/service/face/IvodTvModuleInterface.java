package com.pbtd.manager.vod.tv.mould.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.tv.mould.domain.VodTvModule;

public interface IvodTvModuleInterface {
	  
    int delete(Integer id);
    int deletemodule(Map<String, Object> queryParams);
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
    /**
     * 标记未同步 
     * @param moduleId->channel
     */
    int updateflag(Map<String, Object> queryParams);
    int updateflagforchannel(Map<String, Object> queryParams);
    /**
     * 标记频道-模块-专辑同步状态
     * @param flag,[channelCode]
     */
    int updateChannelModuleFlag(Map<String,Object> params) throws Exception;
    int deletealbuminfo(Map<String, Object> queryParams);
    int updateimg(Map<String, Object> queryParams);
    int updateshangxian(Map<String, Object> queryParams);

    List<Map<String,Object>> getModuleAlbum(Map<String,Object> queryParams);
	List<Map<String,Object>> getModuleAlbumforOne(Map<String,Object> queryParams);
	
	int updateMoudleSeq(String nodes,String treeNodeArr,String targetNodeArr) throws Exception;
}