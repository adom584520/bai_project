package com.pbtd.manager.inject.tv.mapper;

public interface InjectTvBehindPriorityMapper {
	
	int saveNewAlbum();
	
	int saveNewVideo();
	
	int updateNewAlbumPriority();
	
	int updateNewVideoPriority();
	
	int updateNewFollowVideoPriority();
	
	int findNewVideoNum();
	
	/**
	 * 根据剧集状态 更新专辑状态 
	 * @return
	 */
	int updateAllAlbumInjectState();
	
}
