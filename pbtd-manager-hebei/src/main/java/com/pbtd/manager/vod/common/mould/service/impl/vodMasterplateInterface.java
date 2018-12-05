package com.pbtd.manager.vod.common.mould.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.common.mould.domain.vodMasterplate;
import com.pbtd.manager.vod.common.mould.mapper.vodMasterplateMapper;
import com.pbtd.manager.vod.common.mould.service.face.IvodMasterplateInterface;
@Service
public class vodMasterplateInterface implements IvodMasterplateInterface {

	 @Autowired
	    private vodMasterplateMapper  masterplateMapper;
	 
	 
	@Override
	public int delete(Map<String, Object> queryParams) {
		
		return masterplateMapper.delete(queryParams);
	}

	@Override
	public int insert(vodMasterplate m) {
		
		return masterplateMapper.insert(m);
	}

	@Override
	public vodMasterplate load(Integer id) {
		
		return masterplateMapper.load(id);
	}

	@Override
	public int update(vodMasterplate m) {
		
		return masterplateMapper.update(m);
	}

	@Override
	public List<vodMasterplate> find(Map<String, Object> queryParams) {
		
		return masterplateMapper.find(queryParams);
	}

	@Override
	public List<vodMasterplate> page(Map<String, Object> queryParams) {
		
		return masterplateMapper.page(queryParams);
	}

	@Override
	public int count(Map<String, Object> queryParams) {
		
		return masterplateMapper.count(queryParams);
	}

	@Override
	public int updateimg(Map<String, Object> queryParams) {
		return masterplateMapper.updateimg(queryParams);
	}

}
