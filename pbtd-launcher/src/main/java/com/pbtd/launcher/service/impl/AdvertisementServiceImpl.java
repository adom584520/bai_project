package com.pbtd.launcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.launcher.domain.Advertisement;
import com.pbtd.launcher.mapper.AdvertisementMapper;
import com.pbtd.launcher.service.AdvertisementService;
import com.pbtd.launcher.util.LauncherConstant;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
	@Autowired
	private AdvertisementMapper advertisementMapper;

	@Override
	public Advertisement queryStartAdvByGroupId(Long groupId) {
		return advertisementMapper.queryStartAdvByGroupId(groupId, LauncherConstant.ADVERTISEMENT_TYPE_STARTING_UP, LauncherConstant.UPLINE_STATUS);
	}

	@Override
	public Advertisement queryLauncherAdvByGroupId(Long groupId) {
		return advertisementMapper.queryStartAdvByGroupId(groupId,LauncherConstant.ADVERTISEMENT_TYPE_LAUNCHER_START,LauncherConstant.UPLINE_STATUS);
	}
}