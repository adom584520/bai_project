package com.pbtd.manager.inject.tv.mapper;

import java.util.Map;

public interface InjectTvAlbumVideoVersionMapper {

	public Map<String,Object> findVersionBySeriesAndDramaAndVersion(Map<String,Object> map);
	
	public void saveZxVideoVersion(Map<String,Object> map);
	
	public void saveHwVideoVersion(Map<String,Object> map);
	
	public void updateZxVideoVersion(Map<String,Object> map);
	
	public void updateHwVideoVersion(Map<String,Object> map);
	
	/**
	 * 根据码率表更新剧集状态 中兴
	 * @param map：seriesCode drama
	 */
	public void updateZxVideoStateFromVersion(Map<String,Object> map);
	
	/**
	 * 根据码率表更新剧集状态 华为
	 * @param map：seriesCode drama
	 */
	public void updateHwVideoStateFromVersion(Map<String,Object> map);
}
