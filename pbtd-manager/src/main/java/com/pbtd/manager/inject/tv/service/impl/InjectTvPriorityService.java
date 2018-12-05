package com.pbtd.manager.inject.tv.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.inject.phone.domain.InjectPriority;
import com.pbtd.manager.inject.tv.mapper.InjectTvPriorityMapper;
import com.pbtd.manager.inject.tv.service.face.IInjectTvPriorityService;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
@Service
public class InjectTvPriorityService implements IInjectTvPriorityService {

	
	@Autowired
	private InjectTvPriorityMapper injectTvPriorityMapper;

	@Override
	public int count(Map<String, Object> map) {
		return this.injectTvPriorityMapper.count(map);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> map) {
		return this.injectTvPriorityMapper.find(map);
	}

	@Override
	public List<Map<String, Object>> findChannels() {
		return this.injectTvPriorityMapper.findChannels();
	}

	@Override
	public List<Map<String, Object>> findPartChannels() {
		return this.injectTvPriorityMapper.findPartChannels();
	}

	@Override
	public List<Map<String, Object>> findAllChannels() {
		return this.injectTvPriorityMapper.findAllChannels();
	}

	@Override
	public int savePriority(Map<String, Object> map) {
		return this.injectTvPriorityMapper.savePriority(map);
	}

	@Override
	public Map<String, Object> findById(Map<String, Object> map) {
		return this.injectTvPriorityMapper.findById(map);
	}

	@Override
	public int deletePriority(Map<String, Object> map) {
		return this.injectTvPriorityMapper.deletePriority(map);
	}

	@Override
	public int updatePriority(Map<String, Object> map) {
		return this.injectTvPriorityMapper.updatePriority(map);
	}

	

}
