package com.pbtd.manager.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.system.domain.RealmName;
import com.pbtd.manager.system.mapper.RealmNameMapper;
import com.pbtd.manager.system.service.IRealmNameService;


@Service
public class RealmNameService implements IRealmNameService{
    
	@Autowired
	private RealmNameMapper realmnameMapper;
	
	@Override
	public List<Map<String, Object>> find(Map<String, Object> queryParams) {
		return realmnameMapper.find(queryParams);
	}

	@Override
	public RealmName load(int id) {
		return realmnameMapper.load(id);
	}

	@Override
	public int insert(Map<String,Object> queryParams) {
		return realmnameMapper.insert(queryParams);
	}

	@Override
	public int update(Map<String,Object> queryParams) {
		return realmnameMapper.update(queryParams);
	}

	@Override
	public int delete(Map<String,Object>queryParams) {
		return realmnameMapper.delete(queryParams);
	}

	@Override
	public int count(Map<String, Object> queryParams) {
		return realmnameMapper.count(queryParams);
	}

	@Override
	public Map<String, Object>  findtitle(Map<String, Object> queryParams) {
		return realmnameMapper.findtitle(queryParams);
	}

}
