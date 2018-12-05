package com.pbtd.launcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.launcher.domain.Log;
import com.pbtd.launcher.mapper.LogMapper;
import com.pbtd.launcher.service.LogService;

@Service
public class LogserviceImpl implements LogService {
	@Autowired
	private LogMapper logMapper;

	@Override
	public void insert(Log log) {
		logMapper.insert(log);
	}

}
