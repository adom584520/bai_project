package com.pbtd.manager.vod.phone.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.common.mapper.PlayPolicyalbumMapper;
import com.pbtd.manager.vod.phone.common.service.face.IPlayPolicyalbumService;
@Service
public class PlayPolicyalbumService implements IPlayPolicyalbumService {

	@Autowired
	private PlayPolicyalbumMapper playmapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
		
		return playmapper.count(queryParams);
	}

	@Override
	public List<Map<String, Object>> page(Map<String, Object> queryParams) {
		
		return playmapper.page(queryParams);
	}

	@Override
	public Map<String, Object> load(int id) {
		
		return playmapper.load(id);
	}

	@Override
	public int insert(Map<String, Object> m) {
		
		return playmapper.insert(m);
	}

	@Override
	public int update(Map<String, Object> m) {
		
		return playmapper.update(m);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		
		return playmapper.deletes(ids);
	}
	

	@Override
	public int updateisshow(Map<String, Object> queryParams) {
		return playmapper.updateyoukuisshow(queryParams);
	}

}
