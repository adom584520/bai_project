package com.pbtd.manager.inject.tv.mapper;

import java.util.List;
import java.util.Map;

public interface InjectTvAlbumVideoMapper {

	public Integer countalbumVideo(Map<String,Object> map);
	
	public Map<String,Object> countalbumVideoAllAndSuccess(Map<String,Object> map);
	
	public List<Map<String,Object>> pagealbumVideo(Map<String,Object> map);
	
	public void updateAlbumVideoPriority(Map<String,Object> map);
	
	public void updateHwAlbumVideoPriority(Map<String,Object> map);
	
	public void updateZxAlbumVideoPriority(Map<String,Object> map);
	
	public void updateAlbumVideoHwInjectState(Map<String,Object> map);
	
	public void updateAlbumVideoZxInjectState(Map<String,Object> map);
	
	/**
	 *  更新专辑注入状态  某一专辑
	 * @param map：seriesCode
	 */
	public void updateSingleAlbumInjectState(Map<String,Object> map);
}
