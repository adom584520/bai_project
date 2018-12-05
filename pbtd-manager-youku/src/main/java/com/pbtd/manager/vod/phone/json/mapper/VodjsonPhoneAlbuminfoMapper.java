package com.pbtd.manager.vod.phone.json.mapper;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.vod.phone.json.domain.VodjsonPhoneAlbuminfo;

public interface VodjsonPhoneAlbuminfoMapper {

    int deleteByPrimaryKey(String cpseriescode);

    int insert(VodjsonPhoneAlbuminfo albumin);

    VodjsonPhoneAlbuminfo selectByPrimaryKey(String cpseriescode);

    int updateByPrimaryKey(@Param("currentnum")Integer currentnum,@Param("cpseriescode")String cpseriescode);
}