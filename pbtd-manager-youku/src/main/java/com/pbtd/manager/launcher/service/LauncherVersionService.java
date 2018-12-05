package com.pbtd.manager.launcher.service;

import com.pbtd.manager.launcher.domain.LauncherVersion;

public interface LauncherVersionService {
	/**
	 * 根据类型查询版本号
	 * @param type
	 * @return
	 */
	LauncherVersion queryByType(Long groupId,Integer type);

	/**
	 * 添加版本信息
	 * @param version
	 */
	void insert(Long groupId);

	/**
	 * 修改版本信息
	 * @param groupId
	 * @param type
	 */
	void update(Long groupId,Integer type);
}
