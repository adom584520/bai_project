package com.pbtd.manager.vod.phone.mould.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.phone.mould.domain.VodPhoneModule;


public interface IVodPhoneModuleInterface {
	  
    int delete(Integer id);
    int deletemodule(Map<String, Object> queryParams);
    Long insert(VodPhoneModule m);
    VodPhoneModule load(Map<String, Object> queryParams);
    int update(VodPhoneModule m);
    List<Map<String, Object>> find(Map<String, Object> queryParams);
    int findMaxModuleSeq(Map<String,Object> param);
    List<VodPhoneModule> page(Map<String, Object> queryParams);
    int count(Map<String, Object> queryParams);
    
    
    List<Map<String, Object>> pagealbum(Map<String, Object> queryParams);
    int countalbum(Map<String, Object> queryParams);
    
    int insertalbum(Map<String, Object> queryParams);
    /**
     * 标记未同步 
     * @param moduleId->channel
     */
    int updateflag(Map<String, Object> queryParams);
    int updatechannelflag(Map<String, Object> queryParams);
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