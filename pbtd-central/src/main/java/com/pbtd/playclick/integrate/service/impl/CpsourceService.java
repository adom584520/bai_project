package com.pbtd.playclick.integrate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.integrate.domain.Cpsource;
import com.pbtd.playclick.integrate.domain.Strategy;
import com.pbtd.playclick.integrate.mapper.CpsourceMapper;
import com.pbtd.playclick.integrate.service.face.ICpsourceService;
@Service
public class CpsourceService implements ICpsourceService {

	   @Autowired
		 private CpsourceMapper cpsourceMapper;	
		
	   
	@Override
	public int count(Map<String, Object> queryParams) {
		return cpsourceMapper.count(queryParams);
	}

	@Override
	public List<Strategy> find(Map<String, Object> queryParams) {
		
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
