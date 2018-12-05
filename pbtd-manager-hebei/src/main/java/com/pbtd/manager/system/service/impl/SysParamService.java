package com.pbtd.manager.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.system.domain.SysParam;
import com.pbtd.manager.system.domain.SysParamList;
import com.pbtd.manager.system.mapper.SysParamListMapper;
import com.pbtd.manager.system.mapper.SysParamMapper;
import com.pbtd.manager.system.service.ISysParamService;
@Service
public class SysParamService implements ISysParamService {
	@Autowired
	private SysParamMapper sysParamMapper;
	@Autowired
	private SysParamListMapper sysParamListMapper;

	@Override
	public Map<String, Object> getSysParam(Map<String, Object> queryParams) {
		
		return null;
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> queryParams) {
		return sysParamMapper.find(queryParams);

	}

	@Override
	public int count(Map<String, Object> queryParams) {
		return sysParamMapper.count(queryParams);

	}

	@Override
	public SysParam load(int id) {
		return sysParamMapper.load(id);
	}

	@Override
	public int insert(Map<String, Object> queryParams) {
		//String keydata = (String) queryParams.get("keydata");
		//String valuedata = queryParams.get("valuedata").toString();
		SysParamList syslist = sysParamListMapper.findone(queryParams);
		if(syslist != null){
			queryParams.put("keyname", syslist.getKeyname());
			queryParams.put("valuename", syslist.getValuename());
		}
		return sysParamMapper.insert(queryParams);
	}

	@Override
	public int update(Map<String, Object> queryParams) {
		SysParamList syslist = sysParamListMapper.findone(queryParams);
		if(syslist != null){
			queryParams.put("keyname", syslist.getKeyname());
			queryParams.put("valuename", syslist.getValuename());
		}
		return sysParamMapper.update(queryParams);
	}

	@Override
	public int delete(Map<String, Object> queryParams) {
		return sysParamMapper.delete(queryParams);
	}

	
}
