package com.pbtd.manager.inject.tv.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InjectTvBehindPriorityMapper {
	
	int saveNewAlbum();
	
	int saveNewVideo();
	
	int updateNewAlbumPriority();
	
	int updateNewVideoPriority();
	
	int updateNewFollowVideoPriority();
	
	int findNewVideoNum();
	
}
