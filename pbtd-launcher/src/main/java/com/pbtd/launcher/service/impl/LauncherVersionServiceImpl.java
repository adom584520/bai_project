package com.pbtd.launcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.launcher.domain.LauncherVersion;
import com.pbtd.launcher.mapper.LauncherVersionMapper;
import com.pbtd.launcher.service.LauncherVersionService;

@Service
public class LauncherVersionServiceImpl implements LauncherVersionService {
	@Autowired
	private LauncherVersionMapper launcherVersionMapper;

	@Override
	public LauncherVersion queryByType(Long groupId,Integer type) {
		return launcherVersionMapper.queryByType(groupId,type);
	}
}