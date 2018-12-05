package com.pbtd.playclick.integrate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.integrate.domain.Strategy;
import com.pbtd.playclick.integrate.mapper.StrategyMapper;
import com.pbtd.playclick.integrate.service.face.IStrategyService;
@Service
public class StrategyService implements IStrategyService {
	@Autowired
	 private StrategyMapper strategyMapper;	
	  
	@Override
	public int count(Map<String, Object> queryParams) {
		return strategyMapper.count(queryParams);
	}

	@Override
	public List<Strategy> page(int start, int limit, Map<String, Object> queryParams) {
		
		return strategyMapper.page(start, limit, queryParams);
	}

	@Override
	public List<Strategy> find(Map<String, Object> queryParams) {
		
		return strategyMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		return strategyMapper.generatePosition(queryParams);
	}

	@Override
	public Strategy load(int id) {
		return strategyMapper.load(id);
	}

	@Override
	public int insert(Strategy Strategy) {
		return strategyMapper.insert(Strategy);
	}

	@Override
	public int update(Strategy Strategy) {
		return strategyMapper.update(Strategy);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		return strategyMapper.deletes(ids);
	}

}
