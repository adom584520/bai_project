package com.pbtd.vodinterface.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.vodinterface.service.LogService;
import com.pbtd.vodinterface.web.domain.Log;
import com.pbtd.vodinterface.web.mapper.LogMapper;

@Service
public class LogserviceImpl implements LogService {
	@Autowired
	private LogMapper logMapper;

	@Override
	public void insert(Log log) {
		logMapper.insert(log);
	}

}
