package com.pbtd.launcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.launcher.domain.LauncherUI;
import com.pbtd.launcher.mapper.LauncherUIMapper;
import com.pbtd.launcher.service.LauncherUIService;
import com.pbtd.launcher.util.LauncherConstant;

@Service
public class LauncherUIServiceImpl implements LauncherUIService {
	@Autowired
	private LauncherUIMapper launcherUIMapper;

	@Override
	public LauncherUI queryByGroupId(Long groupId) {
		return launcherUIMapper.queryByGroupId(groupId, LauncherConstant.UPLINE_STATUS);
	}

}