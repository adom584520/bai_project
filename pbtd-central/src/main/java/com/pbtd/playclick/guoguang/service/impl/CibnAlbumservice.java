package com.pbtd.playclick.guoguang.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.guoguang.domain.GgAlbumsinfo;
import com.pbtd.playclick.guoguang.domain.GgAlbumsinfovideo;
import com.pbtd.playclick.guoguang.mapper.CibnAlbumMapper;
import com.pbtd.playclick.guoguang.service.ICibnAlbumservice;
@Service
public class CibnAlbumservice implements ICibnAlbumservice {

	@Autowired
	private CibnAlbumMapper cibhalbummapper;

	@Override
	public int count(Map<String, Object> queryParams) {
		
		return cibhalbummapper.count(queryParams);
	}

	@Override
	public List<GgAlbumsinfo> page(int start, int limit, Map<String, Object> queryParams) {
		
		return cibhalbummapper.page(start, limit, queryParams);
	}

	@Override
	public List<GgAlbumsinfo> find(Map<String, Object> queryParams) {
		
		return cibhalbummapper.find(queryParams);
	}

	@Override
	public GgAlbumsinfo load(Map<String, Object> queryParams  ) {
		return cibhalbummapper.load(queryParams);
	}

	@Override
	public int insert(GgAlbumsinfo albums) {
		
		return cibhalbummapper.insert(albums);
	}

	@Override()
	public int update(GgAlbumsinfo albums) {
		
		return cibhalbummapper.update(albums);
	}

	@Override
	public List<GgAlbumsinfovideo> findAlbumsinfovideo(Map<String, Object> queryParams) {
		return cibhalbummapper.findAlbumsinfovideo(queryParams);
	}
	 
	 

}
