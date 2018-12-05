package com.pbtd.manager.vod.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.inject.Scope.Strategy;
import com.pbtd.manager.vod.system.domain.Cpsource;
import com.pbtd.manager.vod.system.mapper.CpsourceMapper;
import com.pbtd.manager.vod.system.service.face.ICpsourceService;
@Service
public class CpsourceService implements ICpsourceService {

	   @Autowired
		 private CpsourceMapper cpsourceMapper;	
		
	   
	@Override
	public int count(Map<String, Object> queryParams) {
		return cpsourceMapper.count(queryParams);
	}

	@Override
	public List<Cpsource> find(Map<String, Object> queryParams) {
		
		return cpsourceMapper.find(queryParams);
	}

	@Override
	public Cpsource load(int id) {
		
		return cpsourceMapper.load(id);
	}

	@Override
	public int insert(Cpsource cpsource) {
		return cpsourceMapper.insert(cpsource);
	}

	@Override
	public int update(Cpsource cpsource) {
		return cpsourceMapper.update(cpsource);
	}

	@Override
	public int deletes(Map<String, Object> queryParams) {
		return cpsourceMapper.deletes(queryParams);
	}
 
}
