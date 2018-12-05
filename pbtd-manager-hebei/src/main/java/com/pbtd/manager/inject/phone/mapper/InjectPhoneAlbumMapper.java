package com.pbtd.manager.inject.phone.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

public interface InjectPhoneAlbumMapper {

	public Integer countalbum(Map<String,Object> map);
	
	public List<Map<String,Object>> findInjectAlbum(Map<String,Object> map);
	
	public void saveInjectAlbum(Map<String,Object> map);
	
	public List<Map<String,Object>> pagealbum(Map<String,Object> map);
	
	public void updateAlbumPriority(Map<String,Object> map);
	
	public void updateAlbumInjectState(Map<String,Object> map);
	
}
