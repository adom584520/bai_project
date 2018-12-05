package com.pbtd.manager.vod.phone.json.mapper;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.vod.phone.json.domain.VodjsonPhoneAlbuminfo;

public interface VodjsonPhoneAlbuminfoMapper {

    int deleteByPrimaryKey(String seriescode);

    int insert(VodjsonPhoneAlbuminfo albumin);

    VodjsonPhoneAlbuminfo selectByPrimaryKey(@Param("seriescode")String seriescode);

    int updateByPrimaryKey(VodjsonPhoneAlbuminfo albumin);
}