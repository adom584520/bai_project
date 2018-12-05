package com.pbtd.manager.vod.common.mould.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.common.mould.domain.vodMasterplateSon;
import com.pbtd.manager.vod.common.mould.mapper.vodMasterplateSonMapper;
import com.pbtd.manager.vod.common.mould.service.face.IvodMasterplateSonInterface;
@Service
public class vodMasterplateSonInterface implements IvodMasterplateSonInterface {

	 @Autowired
	    private vodMasterplateSonMapper  masterplateSonMapper;
	 
	
	@Override
	public int delete(Map<String, Object> queryParams) {
		
		return masterplateSonMapper.delete(queryParams);
	}

	@Override
	public int insert(vodMasterplateSon m) {
		
		return masterplateSonMapper.insert(m);
	}

	@Override
	public vodMasterplateSon load(Integer id) {
		
		return masterplateSonMapper.load(id);
	}

	@Override
	public int update(vodMasterplateSon m) {
		
		return masterplateSonMapper.update(m);
	}

	@Override
	public List<vodMasterplateSon> find(Map<String, Object> queryParams) {
		
		return masterplateSonMapper.find(queryParams);
	}

	@Override
	public List<vodMasterplateSon> page(Map<String, Object> queryParams) {
		
		return masterplateSonMapper.page(queryParams);
	}

	@Override
	public int count(Map<String, Object> queryParams) {
		
		return masterplateSonMapper.count(queryParams);
	}

}
