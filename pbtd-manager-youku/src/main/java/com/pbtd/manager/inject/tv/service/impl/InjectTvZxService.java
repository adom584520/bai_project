package com.pbtd.manager.inject.tv.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.inject.tv.mapper.InjectTvAlbumMapper;
import com.pbtd.manager.inject.tv.mapper.InjectTvAlbumVideoMapper;
import com.pbtd.manager.inject.tv.mapper.InjectTvAlbumVideoVersionMapper;
import com.pbtd.manager.inject.tv.service.face.IInjectTvZxService;


@Service
public class InjectTvZxService implements IInjectTvZxService {

	@Autowired
	private InjectTvAlbumMapper injectTvAlbumMapper;
	
	@Autowired
	private InjectTvAlbumVideoMapper injectTvAlbumVideoMapper; 
	
	@Autowired
	private InjectTvAlbumVideoVersionMapper injectTvAlbumVideoVersionMapper;
	
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
	public void updateHwAlbumVideoPriority(Map<String, Object> map) {
		this.injectTvAlbumVideoMapper.updateHwAlbumVideoPriority(map);
	}

	@Override
	public void updateZxAlbumVideoPriority(Map<String, Object> map) {
		this.injectTvAlbumVideoMapper.updateZxAlbumVideoPriority(map);
	}

	@Override
	public void updateAlbumInjectState(Map<String, Object> map) {
		this.injectTvAlbumMapper.updateAlbumInjectState(map);
		
	}
	
	@Override
	public void updateAlbumVideoPriority(Map<String, Object> map) {
		this.injectTvAlbumVideoMapper.updateAlbumVideoPriority(map);
	}

	
	@Override
	public Integer countalbumVideo(Map<String, Object> map) {
		return this.injectTvAlbumVideoMapper.countalbumVideo(map);
	}

	@Override
	public Map<String, Object> countalbumVideoAllAndSuccess(Map<String, Object> map) {
		
		return this.injectTvAlbumVideoMapper.countalbumVideoAllAndSuccess(map);
	}
	
	@Override
	public List<Map<String, Object>> pagealbumVideo(Map<String, Object> map) {
		return this.injectTvAlbumVideoMapper.pagealbumVideo(map);
	}

	@Override
	public void updateAlbumVideoHwInjectState(Map<String, Object> map) {
		this.injectTvAlbumVideoMapper.updateAlbumVideoHwInjectState(map);
	}

	@Override
	public void updateAlbumVideoZxInjectState(Map<String, Object> map) {
		this.injectTvAlbumVideoMapper.updateAlbumVideoZxInjectState(map);
		
	}

	@Override
	public Map<String, Object> findVersionBySeriesAndDramaAndVersion(Map<String, Object> map) {
		return this.injectTvAlbumVideoVersionMapper.findVersionBySeriesAndDramaAndVersion(map);
	}

	@Override
	public void saveZxVideoVersion(Map<String, Object> map) {
		this.injectTvAlbumVideoVersionMapper.saveZxVideoVersion(map);
		
	}

	@Override
	public void saveHwVideoVersion(Map<String, Object> map) {
		this.injectTvAlbumVideoVersionMapper.saveHwVideoVersion(map);
	}

	@Override
	public void updateZxVideoVersion(Map<String, Object> map) {
		this.injectTvAlbumVideoVersionMapper.updateZxVideoVersion(map);
	}

	@Override
	public void updateHwVideoVersion(Map<String, Object> map) {
		this.injectTvAlbumVideoVersionMapper.updateHwVideoVersion(map);
	}

	@Override
	public void updateZxVideoStateFromVersion(Map<String, Object> map) {
		this.injectTvAlbumVideoVersionMapper.updateZxVideoStateFromVersion(map);
	}

	@Override
	public void updateHwVideoStateFromVersion(Map<String, Object> map) {
		this.injectTvAlbumVideoVersionMapper.updateHwVideoStateFromVersion(map);
	}

	@Override
	public void updateSingleAlbumInjectState(Map<String, Object> map) {
		this.injectTvAlbumVideoMapper.updateSingleAlbumInjectState(map);
	}

	
	
}
