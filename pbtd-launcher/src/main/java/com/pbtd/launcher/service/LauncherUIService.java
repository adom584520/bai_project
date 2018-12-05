package com.pbtd.launcher.service;

import com.pbtd.launcher.domain.LauncherUI;

public interface LauncherUIService {
	
	/**
	 * 通过分组ID查询已上线的launcherUI信息
	 * @param groupId
	 * @return
	 */
	LauncherUI queryByGroupId(Long groupId);
}
