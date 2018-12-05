package com.pbtd.playuser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playuser.dto.UpgradeDTO;
import com.pbtd.playuser.mapper.UpgradeMapper;
import com.pbtd.playuser.service.UpgradeService;
import com.pbtd.playuser.util.UpgradeConstant;

@Service
public class UpgradeServiceImpl implements UpgradeService {
	@Autowired
	private UpgradeMapper upgradeMapper;

	@Override
	public UpgradeDTO queryValidateVersion(Integer type, String version) {
		return upgradeMapper.queryByTypeStatus(type, UpgradeConstant.UPGRADE_STATUS_NEW);
	}

}