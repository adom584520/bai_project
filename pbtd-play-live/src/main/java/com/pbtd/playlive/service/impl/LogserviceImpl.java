package com.pbtd.playlive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playlive.domain.Log;
import com.pbtd.playlive.mapper.LogMapper;
import com.pbtd.playlive.service.LogService;

@Service
public class LogserviceImpl implements LogService {
	@Autowired
	private LogMapper logMapper;

	@Override
	public void insert(Log log) {
		logMapper.insert(log);
	}

}
