package com.pbtd.launcher.service;

import com.pbtd.launcher.dto.UpgradeDTO;

public interface UpgradeService {
	/**
	 * 校验升级版本
	 * @param type
	 * @param version
	 * @return
	 */
	UpgradeDTO queryValidateVersion(Integer type,String version);
}
