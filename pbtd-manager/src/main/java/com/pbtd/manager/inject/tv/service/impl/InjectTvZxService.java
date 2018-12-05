package com.pbtd.manager.inject.tv.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.inject.tv.mapper.InjectTvAlbumMapper;
import com.pbtd.manager.inject.tv.mapper.InjectTvAlbumVideoMapper;
import com.pbtd.manager.inject.tv.service.face.IInjectTvZxService;

@Service
public class InjectTvZxService implements IInjectTvZxService {

	@Autowired
	private InjectTvAlbumMapper injectTvAlbumMapper;
	
	@Autowired
	private InjectTvAlbumVideoMapper injectTvAlbumVideoMapper; 
	
	public Integer countalbum(Map<String,Object> map){
		return this.injectTvAlbumMapper.countalbum(map);
	}
	
	public List<Map<String,Object>> pagealbum(Map<String,Object> map){
		return  this.injectTvAlbumMapper.pagealbum(map);
	}
	
	public void updateAlbumPriority(Map<String,Object> map){
		this.injectTvAlbumMapper.updateAlbumPriority(map);
	}

	
	@Override
	public Integer countalbumVideo(Map<String, Object> map) {
		return this.injectTvAlbumVideoMapper.countalbumVideo(map);
	}

	@Override
	public List<Map<String, Object>> pagealbumVideo(Map<String, Object> map) {
		return this.injectTvAlbumVideoMapper.pagealbumVideo(map);
	}

	@Override
	public void updateAlbumVideoPriority(Map<String, Object> map) {
		this.injectTvAlbumVideoMapper.updateAlbumVideoPriority(map);
	}

	@Override
	public void updateAlbumInjectState(Map<String, Object> map) {
		this.injectTvAlbumMapper.updateAlbumInjectState(map);
		
	}
	
	
	
	
}
