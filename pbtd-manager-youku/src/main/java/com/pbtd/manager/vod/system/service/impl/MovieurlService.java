package com.pbtd.manager.vod.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.system.domain.Movieurl;
import com.pbtd.manager.vod.system.mapper.MovieurlMapper;
import com.pbtd.manager.vod.system.service.face.IMovieurlService;

@Service
public class MovieurlService implements IMovieurlService {

	@Autowired
	private  MovieurlMapper movieurlmapper; 
	@Override
	public int insert(Movieurl map) {
		
		return movieurlmapper.insert(map);
	}

	@Override
	public int update(Movieurl map) {
		
		return movieurlmapper.update(map);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		
		return movieurlmapper.updateStatus(map);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		
		return movieurlmapper.deletes(ids);
	}

	@Override
	public List<Movieurl> page(Map<String, Object> queryParams) {
		
		return movieurlmapper.page(queryParams);
	}

	@Override
	public List<Movieurl> find(Map<String, Object> queryParams) {
		
		return movieurlmapper.find(queryParams);
	}

	@Override
	public Movieurl load(int id) {
		
		return movieurlmapper.load(id);
	}

	@Override
	public int count(Map<String, Object> queryParams) {
		
		return movieurlmapper.count(queryParams);
	}

}
