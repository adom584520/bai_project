package com.pbtd.playclick.integrate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.integrate.domain.VodAlbuminfo;
import com.pbtd.playclick.integrate.mapper.VodAlbuminfoMapper;
import com.pbtd.playclick.integrate.service.face.IVodAlbuminfoService;
@Service
public class VodAlbuminfoService implements IVodAlbuminfoService {


	   @Autowired
	 private VodAlbuminfoMapper vodAlbuminfoMapper;	
	
	@Override
	public int count(Map<String, Object> queryParams) {
		
		return vodAlbuminfoMapper.count(queryParams);
	}

	@Override
	public List<VodAlbuminfo> page(int start, int limit, Map<String, Object> queryParams) {
		
		return vodAlbuminfoMapper.page(start, limit, queryParams);
	}

	@Override
	public List<VodAlbuminfo> find(Map<String, Object> queryParams) {
		
		return vodAlbuminfoMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		
		return vodAlbuminfoMapper.generatePosition(queryParams);
	}

	@Override
	public VodAlbuminfo load(Map<String, Object> queryParams) {
		
		return vodAlbuminfoMapper.load(queryParams);
	}

	@Override
	public int insert(VodAlbuminfo vodAlbuminfo) {
		
		return vodAlbuminfoMapper.insert(vodAlbuminfo);
	}

	@Override
	public int update(VodAlbuminfo vodAlbuminfo) {
		
		return vodAlbuminfoMapper.update(vodAlbuminfo);
	}

	@Override
	public int deletes(	Map<String, Object> queryParams) {
		
		return vodAlbuminfoMapper.deletes(queryParams);
	}

	@Override
	public List<Map<String, Object>> findAlbumsinfovideo(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.findAlbumsinfovideo(queryParams);
	}

	@Override
	public int updateissue(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.updateissue(queryParams);
	}

	@Override
	public int countAlbumsinfovideo(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.countAlbumsinfovideo(queryParams);
	}
	@Override
	public int insertvideo(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.insertvideo(queryParams);
	}

	@Override
	public int deletesvideo(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.deletesvideo(queryParams);
	}

}
