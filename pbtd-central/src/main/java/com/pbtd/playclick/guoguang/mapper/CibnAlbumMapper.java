package com.pbtd.playclick.guoguang.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.guoguang.domain.GgAlbumsinfo;
import com.pbtd.playclick.guoguang.domain.GgAlbumsinfovideo;
@Mapper
public interface CibnAlbumMapper {
	 /**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

    /**
     * 模糊获取符合查询条件的分页记录
     */
    List<GgAlbumsinfo> page(int start, int limit, Map<String, Object> queryParams);

    /**
     * 精确获取符合查询条件的记录
     */
    List<GgAlbumsinfo> find(Map<String, Object> queryParams);

    /**
     * 根据标识获取记录
     */
    GgAlbumsinfo load(Map<String, Object> queryParams);

    /**
     * 插入记录
     */
    int insert(GgAlbumsinfo albums);

    /**
     * 修改记录
     */
    int update(GgAlbumsinfo albums);
    /**播放明细
     */
    List<GgAlbumsinfovideo> findAlbumsinfovideo(Map<String, Object> queryParams);
}
