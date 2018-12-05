package com.pbtd.manager.vod.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.system.domain.Logo;
import com.pbtd.manager.vod.system.mapper.LogoMapper;
import com.pbtd.manager.vod.system.service.face.ILogoService;


@Service
public class LogoService implements ILogoService{
    
	@Autowired
	private LogoMapper logoMapper;
	
	@Override
	public List<Map<String, Object>> find(Map<String, Object> queryParams) {
		return logoMapper.find(queryParams);
	}

	@Override
	public Logo load(int id) {
		return logoMapper.load(id);
	}

	@Override
	public int add(Map<String,Object> queryParams) {
		return logoMapper.add(queryParams);
	}

	@Override
	public int modify(Map<String,Object> queryParams) {
		return logoMapper.modify(queryParams);
	}

	@Override
	public int delete(Map<String,Object>queryParams) {
		return logoMapper.delete(queryParams);
	}

	@Override
	public int count(Map<String, Object> queryParams) {
		return logoMapper.count(queryParams);
	}

}
