package com.pbtd.playuser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playuser.domain.Log;
import com.pbtd.playuser.mapper.LogMapper;
import com.pbtd.playuser.service.LogService;

@Service
public class LogserviceImpl implements LogService {
	@Autowired
	private LogMapper logMapper;

	@Override
	public void insert(Log log) {
		logMapper.insert(log);
	}

}
