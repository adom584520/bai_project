package com.pbtd.playclick.heyi.service;

import java.util.List;
import java.util.Map;

import com.pbtd.playclick.heyi.domain.GuttvAlbuminfo;

public interface IGuttvAlbumservice {

    /**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

    /**
     * 模糊获取符合查询条件的分页记录
     */
    List<GuttvAlbuminfo> page( Map<String, Object> queryParams);

    /**
     * 精确获取符合查询条件的记录
     */
    List<GuttvAlbuminfo> find(Map<String, Object> queryParams);

    /**
     * 根据标识获取记录
     */
    GuttvAlbuminfo load(Map<String, Object> queryParams  );

    /**
     * 插入记录
     */
    int insert(GuttvAlbuminfo albums);

    /**
     * 修改记录
     */
    int update(GuttvAlbuminfo albums);

    /**播放明细
     */
    List<Map<String, Object>> findAlbumsinfovideo(Map<String, Object> queryParams);
}
