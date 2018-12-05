package com.pbtd.manager.inject.phone.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.inject.phone.mapper.InjectPhoneAlbumMapper;
import com.pbtd.manager.inject.phone.mapper.InjectPhoneAlbumVideoMapper;
import com.pbtd.manager.inject.phone.mapper.InjectPhoneAlbumVideoVersionMapper;
import com.pbtd.manager.inject.phone.service.face.IInjectPhoneZxService;

@Service
public class InjectPhoneZxService implements IInjectPhoneZxService {

	@Autowired
	private InjectPhoneAlbumMapper injectPhoneAlbumMapper;
	
	@Autowired
	private InjectPhoneAlbumVideoMapper injectPhoneAlbumVideoMapper; 
	
	@Autowired
	private InjectPhoneAlbumVideoVersionMapper injectPhoneAlbumVideoVersionMapper;
	
	public Integer countalbum(Map<String,Object> map){
		return this.injectPhoneAlbumMapper.countalbum(map);
	}
	
	public List<Map<String,Object>> pagealbum(Map<String,Object> map){
		return  this.injectPhoneAlbumMapper.pagealbum(map);
	}
	
	@Override
	public Map<String,Object> findAlbumVideoBySeriesAndDrama(Map<String, Object> map) {
		return this.injectPhoneAlbumVideoMapper.findAlbumVideoBySeriesAndDrama(map);
	}

	public void updateAlbumPriority(Map<String,Object> map){
		this.injectPhoneAlbumMapper.updateAlbumPriority(map);
	}

	@Override
	public void updateHwAlbumVideoPriority(Map<String, Object> map) {
		this.injectPhoneAlbumVideoMapper.updateHwAlbumVideoPriority(map);
	}

	@Override
	public void updateZxAlbumVideoPriority(Map<String, Object> map) {
		this.injectPhoneAlbumVideoMapper.updateZxAlbumVideoPriority(map);
	}

	@Override
	public void updateAlbumInjectState(Map<String, Object> map) {
		this.injectPhoneAlbumMapper.updateAlbumInjectState(map);
		
	}
	
	@Override
	public void updateAlbumVideoPriority(Map<String, Object> map) {
		this.injectPhoneAlbumVideoMapper.updateAlbumVideoPriority(map);
	}

	
	@Override
	public Integer countalbumVideo(Map<String, Object> map) {
		return this.injectPhoneAlbumVideoMapper.countalbumVideo(map);
	}

	@Override
	public List<Map<String, Object>> pagealbumVideo(Map<String, Object> map) {
		return this.injectPhoneAlbumVideoMapper.pagealbumVideo(map);
	}

	@Override
	public void updateAlbumVideoHwInjectState(Map<String, Object> map) {
		this.injectPhoneAlbumVideoMapper.updateAlbumVideoHwInjectState(map);
	}

	@Override
	public void updateAlbumVideoZxInjectState(Map<String, Object> map) {
		this.injectPhoneAlbumVideoMapper.updateAlbumVideoZxInjectState(map);
		
	}

	@Override
	public Map<String, Object> countalbumVideoAllAndSuccess(Map<String, Object> map) {
		
		return this.injectPhoneAlbumVideoMapper.countalbumVideoAllAndSuccess(map);
	}

	@Override
	public Map<String,Object> findVersionBySeriesAndDramaAndVersion(Map<String, Object> map) {
		return this.injectPhoneAlbumVideoVersionMapper.findVersionBySeriesAndDramaAndVersion(map);
	}

	@Override
	public void saveZxVideoVersion(Map<String, Object> map) {	
		this.injectPhoneAlbumVideoVersionMapper.saveZxVideoVersion(map);
	}

	@Override
	public void saveHwVideoVersion(Map<String, Object> map) {
		this.injectPhoneAlbumVideoVersionMapper.saveHwVideoVersion(map);
	}

	@Override
	public void updateZxVideoVersion(Map<String, Object> map) {
		this.injectPhoneAlbumVideoVersionMapper.updateZxVideoVersion(map);
	}

	@Override
	public void updateHwVideoVersion(Map<String, Object> map) {
		this.injectPhoneAlbumVideoVersionMapper.updateHwVideoVersion(map);
	}

	@Override
	public void updateZxVideoStateFromVersion(Map<String, Object> map) {
		this.injectPhoneAlbumVideoVersionMapper.updateZxVideoStateFromVersion(map);
	}

	@Override
	public void updateHwVideoStateFromVersion(Map<String, Object> map) {
		this.injectPhoneAlbumVideoVersionMapper.updateHwVideoStateFromVersion(map);
	}

	@Override
	public void updateSingleAlbumInjectState(Map<String, Object> map) {
		this.injectPhoneAlbumVideoMapper.updateSingleAlbumInjectState(map);
	}

	
}
