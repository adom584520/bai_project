package com.pbtd.manager.vod.tv.mould.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.tv.mould.domain.VodTvLinkAlbumList;

public interface IvodTvLinkAlbumListInterface {
    int delete(Integer id);
    int insert(VodTvLinkAlbumList record);
    VodTvLinkAlbumList load(Integer id);
    int update(VodTvLinkAlbumList record);
    List<VodTvLinkAlbumList> find(Map<String, Object> queryParams);
    List<VodTvLinkAlbumList> page(Map<String, Object> queryParams);
    int count(Map<String, Object> queryParams);
}