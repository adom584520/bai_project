package com.pbtd.manager.vod.phone.mould.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.phone.mould.domain.VodPhoneLinkAlbumList;
@Mapper
public interface VodPhoneLinkAlbumListMapper {
    int delete(Integer id);
    int insert(VodPhoneLinkAlbumListMapper record);
    VodPhoneLinkAlbumList load(Integer id);
    int update(VodPhoneLinkAlbumList record);
    List<VodPhoneLinkAlbumList> find(Map<String, Object> queryParams);
    List<VodPhoneLinkAlbumList> page(Map<String, Object> queryParams);
    int count(Map<String, Object> queryParams);
    
    List<Map<String,Object>> select(Map<String,Object> queryParams);
    VodPhoneLinkAlbumList selectOne(Map<String,Object> queryParams);

    //查询绑定信息的最大和最小排序
    Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map);
    //查询绑定信息排序
    List<Map<String, Object>> findalbumsequence(Map<String, Object> map);
    
    //查询更改数据的历史排序
    List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams);
    //更改绑定信息排序
    int updatealbumsequence(Map<String, Object> map);
}