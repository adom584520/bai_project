package com.pbtd.manager.vod.system.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.system.domain.Cpsource;
import com.pbtd.manager.vod.system.mapper.CpsourceMapper;
import com.pbtd.manager.vod.system.service.face.CpsourceService;

@Service
public class CpsourceServiceImpl implements CpsourceService {
     
	@Autowired
	private CpsourceMapper cpsourcemapper;
	
	@Override
	public int insert(Cpsource cpsource) {
		return cpsourcemapper.insert(cpsource);
	}

	@Override
	public int count(Map<String, Object> map) {
		return cpsourcemapper.count(map);
	}

}
