package com.pbtd.playclick.heyi.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.heyi.domain.GuttvAlbuminfo;
import com.pbtd.playclick.heyi.mapper.GuttvAlbumMapper;
import com.pbtd.playclick.heyi.service.IGuttvAlbumservice;
@Service
public class GuttvAlbumservice implements IGuttvAlbumservice {

	 @Autowired
	 private GuttvAlbumMapper guttvalbummapper;

	@Override
	public int count(Map<String, Object> queryParams) {
	
		return guttvalbummapper.count(queryParams);
	}

	@Override
	public List<GuttvAlbuminfo> page( Map<String, Object> queryParams) {
	
		return guttvalbummapper.page(queryParams);
	}

	@Override
	public List<GuttvAlbuminfo> find(Map<String, Object> queryParams) {
	
		return guttvalbummapper.find(queryParams);
	}

	@Override
	public GuttvAlbuminfo load(Map<String, Object> queryParams) {
	
		return guttvalbummapper.load(queryParams);
	}

	@Override
	public int insert(GuttvAlbuminfo albums) {
	
		return guttvalbummapper.insert(albums);
	}

	@Override
	public int update(GuttvAlbuminfo albums) {
	
		return guttvalbummapper.update(albums);
	}

	@Override
	public List<Map<String, Object>> findAlbumsinfovideo(Map<String, Object> queryParams) {
	
		return guttvalbummapper.findAlbumsinfovideo(queryParams);
	}

}
