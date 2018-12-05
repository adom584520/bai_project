package com.pbtd.playclick.yinhe.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pbtd.playclick.yinhe.domain.Gitvvideo;
import com.pbtd.playclick.yinhe.domain.GitvvideoWithBLOBs;

@Mapper
public interface GitvvideoMapper {

	int deleteByPrimaryKey(String tvid);

	int insert(GitvvideoWithBLOBs record);

	int insertSelective(GitvvideoWithBLOBs record);

	GitvvideoWithBLOBs selectByPrimaryKey(@Param("parentId")String parentId,@Param("tvId")String tvId,@Param("playOrder")String playOrder );

	int updateByPrimaryKeySelective(GitvvideoWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(GitvvideoWithBLOBs record);

	int updateByPrimaryKey(Gitvvideo record);

	void updateDramacode();
	
	void updateSequence();//银河剧集排序增量sequence

	int delDrama();// 删除重复的剧集
}