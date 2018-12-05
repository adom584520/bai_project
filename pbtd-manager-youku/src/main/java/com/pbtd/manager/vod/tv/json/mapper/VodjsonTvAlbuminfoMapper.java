package com.pbtd.manager.vod.tv.json.mapper;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.vod.tv.json.domain.VodjsonTvAlbuminfo;

public interface VodjsonTvAlbuminfoMapper {

    int deleteByPrimaryKey(String cpseriescode);

    int insert(VodjsonTvAlbuminfo albumin);

    VodjsonTvAlbuminfo selectByPrimaryKey(String cpseriescode);

    int updateByPrimaryKey(@Param("currentnum")Integer currentnum,@Param("cpseriescode")String cpseriescode);
}