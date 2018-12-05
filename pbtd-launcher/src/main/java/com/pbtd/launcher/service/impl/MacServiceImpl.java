package com.pbtd.launcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.launcher.domain.Mac;
import com.pbtd.launcher.mapper.MacMapper;
import com.pbtd.launcher.service.MacService;

@Service
public class MacServiceImpl implements MacService {
	@Autowired
	private MacMapper macMapper;

	@Override
	public Mac queryByMac(String macName) {
		return macMapper.queryByMac(macName);
	}

}