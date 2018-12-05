package com.pbtd.manager.inject.phone.mapper;

import org.apache.ibatis.annotations.Mapper;

public interface InjectPhoneBehindPriorityMapper {
	
	int saveNewAlbum();
	
	int saveNewVideo();
	
	int updateNewAlbumPriority();
	
	int updateNewVideoPriority();
	
	int updateNewFollowVideoPriority();
	
	int findNewVideoNum();
	
}
