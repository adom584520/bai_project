package com.pbtd.manager.vod.phone.album.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.album.domain.Vodalbuminfovideo;
import com.pbtd.manager.vod.phone.album.mapper.VodAlbuminfovideoMapper;
import com.pbtd.manager.vod.phone.album.service.face.IVodAlbuminfovideoService;
@Service
public class VodAlbuminfovideoService implements IVodAlbuminfovideoService {

	
	@Autowired
	private VodAlbuminfovideoMapper vodAlbuminfovideoMapper; 
	
	@Override
	public int count(Map<String, Object> queryParams) {
		
		return vodAlbuminfovideoMapper.count(queryParams);
	}

	@Override
	public List<Vodalbuminfovideo> page(int start, int limit, Map<String, Object> queryParams) {
		
		return vodAlbuminfovideoMapper.page(start, limit, queryParams);
	}

	@Override
	public List<Vodalbuminfovideo> find(Map<String, Object> queryParams) {
		
		return vodAlbuminfovideoMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		
		return vodAlbuminfovideoMapper.generatePosition(queryParams);
	}

	@Override
	public Vodalbuminfovideo load(int id) {
		
		return vodAlbuminfovideoMapper.load(id);
	}

	@Override
	public int insert(Vodalbuminfovideo vodalbuminfovideo) {
		
		return vodAlbuminfovideoMapper.insert(vodalbuminfovideo);
	}

	@Override
	public int update(Vodalbuminfovideo vodalbuminfovideo) {
		
		return vodAlbuminfovideoMapper.update(vodalbuminfovideo);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		
		return vodAlbuminfovideoMapper.deletes(ids);
	}

	@Override
	public int updatesequce(Map<String, Object> queryParams) {
		
		return vodAlbuminfovideoMapper.updatesequce(queryParams);
	}

	@Override
	public int addsequce(Map<String, Object> queryParams) {
		
		return vodAlbuminfovideoMapper.addsequce(queryParams);
	}

}
