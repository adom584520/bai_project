package com.pbtd.playclick.yinhe.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.yinhe.domain.Albums;
import com.pbtd.playclick.yinhe.domain.AlbumsWithBLOBs;

@Mapper
public interface AlbumsMapper {

    int deleteByPrimaryKey(String albumid);

    int insert(AlbumsWithBLOBs record);

    int insertSelective(AlbumsWithBLOBs record);

    AlbumsWithBLOBs selectByPrimaryKey(String albumId);

    int updateByPrimaryKeySelective(AlbumsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(AlbumsWithBLOBs record);

    int updateByPrimaryKey(Albums record);
}