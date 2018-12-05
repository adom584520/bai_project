package com.pbtd.manager.inject.phone.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.inject.phone.domain.InjectPriority;
import com.pbtd.manager.inject.phone.mapper.InjectPhonePriorityMapper;
import com.pbtd.manager.inject.phone.service.face.IInjectPhonePriorityService;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
@Service
public class InjectPhonePriorityService implements IInjectPhonePriorityService {

	
	@Autowired
	private InjectPhonePriorityMapper injectPriorityMapper;

	@Override
	public int count(Map<String, Object> map) {
		return this.injectPriorityMapper.count(map);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> map) {
		return this.injectPriorityMapper.find(map);
	}

	@Override
	public List<Map<String, Object>> findChannels() {
		return this.injectPriorityMapper.findChannels();
	}

	@Override
	public List<Map<String, Object>> findPartChannels() {
		return this.injectPriorityMapper.findPartChannels();
	}

	@Override
	public List<Map<String, Object>> findAllChannels() {
		return this.injectPriorityMapper.findAllChannels();
	}

	@Override
	public int savePriority(Map<String, Object> map) {
		return this.injectPriorityMapper.savePriority(map);
	}

	@Override
	public Map<String, Object> findById(Map<String, Object> map) {
		return this.injectPriorityMapper.findById(map);
	}

	@Override
	public int deletePriority(Map<String, Object> map) {
		return this.injectPriorityMapper.deletePriority(map);
	}

	@Override
	public int updatePriority(Map<String, Object> map) {
		return this.injectPriorityMapper.updatePriority(map);
	}

	

}
