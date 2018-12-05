package com.pbtd.manager.inject.phone.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.inject.phone.mapper.InjectPhoneAlbumMapper;
import com.pbtd.manager.inject.phone.mapper.InjectPhoneAlbumVideoMapper;
import com.pbtd.manager.inject.phone.service.face.IInjectPhoneZxService;

@Service
public class InjectPhoneZxService implements IInjectPhoneZxService {

	@Autowired
	private InjectPhoneAlbumMapper injectPhoneAlbumMapper;
	
	@Autowired
	private InjectPhoneAlbumVideoMapper injectPhoneAlbumVideoMapper; 
	
	public Integer countalbum(Map<String,Object> map){
		return this.injectPhoneAlbumMapper.countalbum(map);
	}
	
	public List<Map<String,Object>> pagealbum(Map<String,Object> map){
		return  this.injectPhoneAlbumMapper.pagealbum(map);
	}
	
	public void updateAlbumPriority(Map<String,Object> map){
		this.injectPhoneAlbumMapper.updateAlbumPriority(map);
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
	public void updateAlbumVideoPriority(Map<String, Object> map) {
		this.injectPhoneAlbumVideoMapper.updateAlbumVideoPriority(map);
	}

	@Override
	public void updateAlbumInjectState(Map<String, Object> map) {
		this.injectPhoneAlbumMapper.updateAlbumInjectState(map);
		
	}
	
	
	
	
}
