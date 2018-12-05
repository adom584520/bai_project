package com.pbtd.manager.vod.tv.mould.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.tv.mould.domain.VodTvLinkAlbumList;
@Mapper
public interface VodTvLinkAlbumListMapper {
    int delete(Integer id);
    int insert(VodTvLinkAlbumList record);
    VodTvLinkAlbumList load(Integer id);
    int update(VodTvLinkAlbumList record);
    List<VodTvLinkAlbumList> find(Map<String, Object> queryParams);
    List<VodTvLinkAlbumList> page(Map<String, Object> queryParams);
    int count(Map<String, Object> queryParams);
    
    List<Map<String,Object>> select(Map<String,Object> queryParams);
    VodTvLinkAlbumList selectOne(Map<String,Object> queryParams);


}