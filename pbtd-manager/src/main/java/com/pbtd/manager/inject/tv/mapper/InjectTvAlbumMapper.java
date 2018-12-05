package com.pbtd.manager.inject.tv.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InjectTvAlbumMapper {

	public Integer countalbum(Map<String,Object> map);
	
	public List<Map<String,Object>> pagealbum(Map<String,Object> map);
	
	public void updateAlbumPriority(Map<String,Object> map);
	
	public void updateAlbumInjectState(Map<String,Object> map);
}
