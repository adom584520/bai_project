package com.pbtd.manager.inject.phone.mapper;

import java.util.List;
import java.util.Map;

public interface InjectPhoneAlbumVideoMapper {

	public Integer countalbumVideo(Map<String,Object> map);
	
	public Map<String,Object> countalbumVideoAllAndSuccess(Map<String,Object> map);
	
	public List<Map<String,Object>> pagealbumVideo(Map<String,Object> map);
	
	
	public void saveAlbumVideo(Map<String,Object> map);
	/**
	 * 根据seriesCode和drama查询剧集
	 * @param map：seriesCode，drama 
	 * 
	 */
	public List<Map<String,Object>> findAlbumVideoBySeriesAndDrama(Map<String,Object> map);
	
	public void updateAlbumVideoPriority(Map<String,Object> map);
	
	public void updateHwAlbumVideoPriority(Map<String,Object> map);
	
	public void updateZxAlbumVideoPriority(Map<String,Object> map);
	
	public void updateAlbumVideoHwInjectState(Map<String,Object> map);
	
	public void updateAlbumVideoZxInjectState(Map<String,Object> map);
	
	/**
	 * 根据剧集状态更新专辑状态
	 * @param map：seriesCode
	 */
	public void updateSingleAlbumInjectState(Map<String,Object> map);
	
}
