package com.pbtd.manager.inject.phone.service.face;

import java.util.List;
import java.util.Map;

/**
 * 手机-中兴service
 * @author shenjr
 *
 */
public interface IInjectPhoneZxService {
	
	public Integer countalbum(Map<String,Object> map);
	
	public List<Map<String,Object>> pagealbum(Map<String,Object> map);
	
	public void updateAlbumPriority(Map<String,Object> map);
	
	
	
	public Integer countalbumVideo(Map<String,Object> map);
	
	public List<Map<String,Object>> pagealbumVideo(Map<String,Object> map);
	
	public void updateAlbumVideoPriority(Map<String,Object> map);
	
	public void updateAlbumInjectState(Map<String,Object> map);
	
}
