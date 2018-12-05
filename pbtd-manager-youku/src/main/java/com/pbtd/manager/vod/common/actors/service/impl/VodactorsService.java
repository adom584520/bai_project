package com.pbtd.manager.vod.common.actors.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.common.actors.domain.Vodactors;
import com.pbtd.manager.vod.common.actors.mapper.VodactorsMapper;
import com.pbtd.manager.vod.common.actors.service.face.IVodactorsService;
@Service
public class VodactorsService implements IVodactorsService {

	   @Autowired
		 private VodactorsMapper vodactorsMapper;

	@Override
	public int count(Map<String, Object> queryParams) {
		
		return vodactorsMapper.count(queryParams);
	}

	@Override
	public List<Vodactors> page(int start, int limit, Map<String, Object> queryParams) {
		
		return vodactorsMapper.page(start, limit, queryParams);
	}

	@Override
	public List<Vodactors> find(Map<String, Object> queryParams) {
		
		return vodactorsMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		
		return vodactorsMapper.generatePosition(queryParams);
	}

	@Override
	public Vodactors load(int id) {
		
		return vodactorsMapper.load(id);
	}

	@Override
	public int insert(Vodactors vodactors) {
		
		return vodactorsMapper.insert(vodactors);
	}

	@Override
	public int update(Vodactors vodactors) {
		
		return vodactorsMapper.update(vodactors);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		
		return vodactorsMapper.deletes(ids);
	}	
	 
 
}
