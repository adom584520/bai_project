package com.pbtd.vodinterface.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.vodinterface.web.mapper.CommonMapper;
import com.pbtd.vodinterface.web.service.face.ICommonService;
@Service
public class CommonService implements ICommonService {
 
 @Autowired
 private CommonMapper commonMapper;
	
	@Override
	public List<Map<String,Object>> getactors(Map<String, Object> queryParams) {
		return commonMapper.getactors(queryParams);
	}

	@Override
	public List<Map<String, Object>> getactorlist(Map<String, Object> queryParams) {
		
		return commonMapper.getactorlist(queryParams);
	}

	@Override
	public List<Map<String, Object>> getChannel(Map<String, Object> queryParams) {
	
		return commonMapper.getChannellist(queryParams);
	}
	@Override
	public List<Map<String, Object>> getlable(Map<String, Object> queryParams) {
		
		return commonMapper.getlable(queryParams);
	}

	@Override
	public List<Map<String, Object>> getspecial(Map<String, Object> queryParams) {
		return commonMapper.getspecial(queryParams);
	}

	@Override
	public List<Map<String, Object>> getspecialvideo(Map<String, Object> queryParams) {

		return commonMapper.getspecialvideo(queryParams);
	}

	@Override
	public List<Map<String, Object>> findChannel(Map<String, Object> queryParams) {

		return commonMapper.findChannel(queryParams);
	}

	@Override
	public List<Map<String, Object>> getlabeltype(Map<String, Object> queryParams) {
		return commonMapper.getlabeltype(queryParams);
	}
	
}
