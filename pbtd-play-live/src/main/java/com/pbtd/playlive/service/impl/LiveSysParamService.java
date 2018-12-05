package com.pbtd.playlive.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playlive.domain.LiveSysParam;
import com.pbtd.playlive.mapper.LiveSysParamMapper;
import com.pbtd.playlive.service.ILiveSysParamService;

@Service
public class LiveSysParamService implements ILiveSysParamService{
	@Autowired
	private LiveSysParamMapper liveSysParamMapper;

	
	@Override
	public Map<String, Object> querylistallL() {
		List<LiveSysParam> list = liveSysParamMapper.selectAll();
		Map<String, Object>  map = new HashMap<String,Object>(); 
		for (LiveSysParam liveSysParam : list) {
			map.put(liveSysParam.getParamvalueadd(), liveSysParam);
		}
		return map;
	}

}
