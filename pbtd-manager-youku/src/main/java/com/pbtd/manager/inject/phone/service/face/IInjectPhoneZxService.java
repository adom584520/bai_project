package com.pbtd.manager.inject.phone.service.face;

import java.util.List;
import java.util.Map;

/**
 * 手机-中兴service
 * @author shenjr
 *
 */
public interface IInjectPhoneZxService {
	//专辑
	/**
	 * 查找专辑数
	 * @param map
	 * @return
	 */
	public Integer countalbum(Map<String,Object> map);
	
	/**
	 * 查找专辑列表
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> pagealbum(Map<String,Object> map);
	
	/**
	 * 更新中兴专辑优先级
	 * @param map
	 */
	public void updateAlbumPriority(Map<String,Object> map);
	
	/**
	 * 更新中兴专辑注入状态
	 * @param map
	 */
	public void updateAlbumInjectState(Map<String,Object> map);
	
	
	//剧集
	/**
	 * 查找剧集数
	 * @param map
	 * @return
	 */
	public Integer countalbumVideo(Map<String,Object> map);
	
	/**
	 * 查找剧集列表
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> pagealbumVideo(Map<String,Object> map);
	
	/**
	 * 根据seriesCode和drama查找记录
	 * @param map
	 */
	public Map<String,Object> findAlbumVideoBySeriesAndDrama(Map<String,Object> map);
	
	/**
	 * 同时更新中兴，华为剧集优先级
	 * @param map
	 */
	public void updateAlbumVideoPriority(Map<String,Object> map);
	
	/**
	 * 更新华为剧集优先级
	 * @param map
	 */
	public void updateHwAlbumVideoPriority(Map<String,Object> map);
	
	/**
	 * 更新中兴剧集优先级
	 * @param map
	 */
	public void updateZxAlbumVideoPriority(Map<String,Object> map);
	
	/**
	 * 更新华为剧集优先级
	 * @param map
	 */
	public void updateAlbumVideoHwInjectState(Map<String,Object> map);
	
	/**
	 * 更新剧集注入状态
	 * @param map
	 */
	public void updateAlbumVideoZxInjectState(Map<String,Object> map);
	
	/**
	 * 查询某专辑下剧集总数&成功剧集数
	 * @param map
	 * @return
	 */
	public Map<String,Object> countalbumVideoAllAndSuccess(Map<String,Object> map);
	
	
	//码率
	/**
	 * 根据seriesCode，drama，version查码率记录
	 * @param map
	 * @return
	 */
	public Map<String,Object> findVersionBySeriesAndDramaAndVersion(Map<String,Object> map);
	
	/**
	 * 保存中兴码率
	 * @param map
	 */
	public void saveZxVideoVersion(Map<String,Object> map);
	
	/**
	 * 保存华为码率
	 * @param map
	 */
	public void saveHwVideoVersion(Map<String,Object> map);
	
	/**
	 * 更新中兴码率
	 * @param map
	 */
	public void updateZxVideoVersion(Map<String,Object> map);
	
	/**
	 * 更新华为码率
	 * @param map
	 */
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
	
	
	/**
	 * 根据剧集状态更新专辑状态
	 * @param map：seriesCode
	 */
	public void updateSingleAlbumInjectState(Map<String,Object> map);
}
