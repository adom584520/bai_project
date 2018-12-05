package com.pbtd.manager.vod.phone.hotsearch.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.album.domain.Vodalbuminfo;
import com.pbtd.manager.vod.phone.album.mapper.VodAlbuminfoMapper;
import com.pbtd.manager.vod.phone.hotsearch.domain.VodHotSearchInfo;
import com.pbtd.manager.vod.phone.hotsearch.mapper.VodHotSearchMapper;
import com.pbtd.manager.vod.phone.hotsearch.service.face.IVodHotSearchService;

@Service
public class VodHotSearchService implements IVodHotSearchService {
	
 
	@Autowired
	private VodHotSearchMapper vodHotSearchMapper;

	@Override
	public int count(Map<String, Object> queryParams) {
		return vodHotSearchMapper.count(queryParams);
	}

	@Override
	public List<Map<String,Object>>  page(Map<String, Object> queryParams) {
		
		return vodHotSearchMapper.page(queryParams);
	}

	@Override
	public int deletes(VodHotSearchInfo vodHostSearchInfo) {
		
		return vodHotSearchMapper.deletes(vodHostSearchInfo);
	}

	@Override
	public int update(VodHotSearchInfo vodHostSearchInfo) {
		
		return vodHotSearchMapper.update(vodHostSearchInfo);
	}

	@Override
	public int insert(VodHotSearchInfo vodHostSearchInfo) {
		
		return vodHotSearchMapper.insert(vodHostSearchInfo);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> queryParams) {
		return vodHotSearchMapper.find(queryParams);
	}

	@Override
	public List<Map<String, Object>> findalbumsequence(Map<String, Object> map) {
		return vodHotSearchMapper.findalbumsequence(map);
	}

	@Override
	public Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map) {
		return vodHotSearchMapper.findalbummaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams) {
		return vodHotSearchMapper.findalbumsequencesum(queryParams);
	}
	 


	
}
