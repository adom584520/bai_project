package com.pbtd.manager.vod.phone.json.mapper;

import com.pbtd.manager.vod.phone.json.domain.VodjsonPhoneAlbuminfovideo;

public interface VodjsonPhoneAlbuminfovideoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(VodjsonPhoneAlbuminfovideo video);

    VodjsonPhoneAlbuminfovideo selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(VodjsonPhoneAlbuminfovideo video);
    
    int updateSeriesCode();
}