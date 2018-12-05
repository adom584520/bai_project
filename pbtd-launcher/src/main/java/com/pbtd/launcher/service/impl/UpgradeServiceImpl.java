package com.pbtd.launcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.launcher.dto.UpgradeDTO;
import com.pbtd.launcher.exception.JsonMessageException;
import com.pbtd.launcher.mapper.UpgradeMapper;
import com.pbtd.launcher.service.UpgradeService;
import com.pbtd.launcher.util.UpgradeConstant;

@Service
public class UpgradeServiceImpl implements UpgradeService {
	@Autowired
	private UpgradeMapper upgradeMapper;

	@Override
	public UpgradeDTO queryValidateVersion(Integer type, String version) {
		UpgradeDTO upgrade = upgradeMapper.queryByTypeStatus(type, UpgradeConstant.UPGRADE_STATUS_NEW);
		if (upgrade == null) {
			throw new JsonMessageException("暂无升级信息！");
		}
		if (upgrade.getVersion().equals(version)) {
			return null;
		}
		return upgrade;
	}

}