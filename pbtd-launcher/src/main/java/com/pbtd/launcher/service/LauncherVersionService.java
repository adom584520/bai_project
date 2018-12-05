package com.pbtd.launcher.service;

import com.pbtd.launcher.domain.LauncherVersion;

public interface LauncherVersionService {
	/**
	 * 根据类型查询版本号
	 * @param type
	 * @return
	 */
	LauncherVersion queryByType(Long groupId,Integer type);
}
