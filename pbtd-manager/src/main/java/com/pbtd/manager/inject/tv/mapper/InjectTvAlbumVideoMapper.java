package com.pbtd.manager.inject.tv.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InjectTvAlbumVideoMapper {

	public Integer countalbumVideo(Map<String,Object> map);
	
	public List<Map<String,Object>> pagealbumVideo(Map<String,Object> map);
	
	public void updateAlbumVideoPriority(Map<String,Object> map);
	
}
