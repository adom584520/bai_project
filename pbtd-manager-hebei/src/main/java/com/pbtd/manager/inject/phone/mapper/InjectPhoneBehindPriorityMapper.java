package com.pbtd.manager.inject.phone.mapper;

import org.apache.ibatis.annotations.Mapper;

public interface InjectPhoneBehindPriorityMapper {
	
	int saveNewAlbum();
	
	int saveNewVideo();
	
	int updateNewAlbumPriority();
	
	int updateNewVideoPriority();
	
	int updateNewFollowVideoPriority();
	
	int findNewVideoNum();
	
	/**
	 * 更新全部专辑注入状态
	 * @return
	 */
	int updateAllAlbumInjectState();
	
}
