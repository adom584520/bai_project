package com.pbtd.vodinterface.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.vodinterface.web.domain.SysParam;
import com.pbtd.vodinterface.web.mapper.SysParamMapper;
import com.pbtd.vodinterface.web.service.face.ISysParamService;
@Service
public class SysParamService implements ISysParamService {
	@Autowired
	private SysParamMapper sysParamMapper;

	@Override
	public Map<String, Object> getSysParam(Map<String, Object> queryParams) {
		List<SysParam> list = sysParamMapper.select(queryParams);
		Map<String, Object> maps = new HashMap();
		for (SysParam sysParam : list) {
			maps.put(sysParam.getKey(), sysParam.getValue());
		}
		return maps;
	}

	
}
