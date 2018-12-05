package com.pbtd.manager.vod.tv.json.mapper;

import com.pbtd.manager.vod.tv.json.domain.VodjsonTvAlbuminfovideo;

public interface VodjsonTvAlbuminfovideoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(VodjsonTvAlbuminfovideo video);

    VodjsonTvAlbuminfovideo selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(VodjsonTvAlbuminfovideo video);
    
    int updateSeriesCode();
}