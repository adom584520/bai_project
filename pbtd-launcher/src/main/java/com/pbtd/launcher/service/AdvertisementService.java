package com.pbtd.launcher.service;

import com.pbtd.launcher.domain.Advertisement;

public interface AdvertisementService {
	/**
	 * 通过分组ID获取上线的开机广告信息
	 * @param groupId
	 * @return
	 */
	Advertisement queryStartAdvByGroupId(Long groupId);

	/**
	 * 通过分组ID获取上线的launcher启动广告信息
	 * @param groupId
	 * @return
	 */
	Advertisement queryLauncherAdvByGroupId(Long groupId);
}
