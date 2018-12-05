package com.pbtd.manager.launcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.launcher.domain.LauncherVersion;
import com.pbtd.manager.launcher.mapper.LauncherVersionMapper;
import com.pbtd.manager.launcher.service.LauncherVersionService;
import com.pbtd.manager.launcher.util.LauncherConstant;

@Service
public class LauncherVersionServiceImpl implements LauncherVersionService {
	@Autowired
	private LauncherVersionMapper launcherVersionMapper;

	@Override
	public LauncherVersion queryByType(Long groupId,Integer type) {
		return launcherVersionMapper.queryByType(groupId,type);
	}

	@Override
	@Transactional
	public void insert(Long groupId) {
		LauncherVersion version = new LauncherVersion();
		version.setVersion(1L);
		version.setGroupId(groupId);
		version.setType(LauncherConstant.LAUNCHER_VERSION_TYPE_START_ADV);
		launcherVersionMapper.insert(version);
		LauncherVersion version2 = new LauncherVersion();
		version2.setVersion(1L);
		version2.setGroupId(groupId);
		version2.setType(LauncherConstant.LAUNCHER_VERSION_TYPE_START_LAUNCHER);
		launcherVersionMapper.insert(version2);
	}

	@Override
	@Transactional
	public void update(Long groupId,Integer type) {
		LauncherVersion version = launcherVersionMapper.queryByType(groupId,type);
		int row = launcherVersionMapper.update(version);
		if (row < 1) {
			throw new JsonMessageException("修改失败,请稍后后重试！");
		}
	}
}