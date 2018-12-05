package com.pbtd.launcher.service;

import com.pbtd.launcher.domain.Mac;

public interface MacService {
	/**
	 * 通过macName获取mac
	 * @param macName
	 * @return
	 */
	Mac queryByMac(String macName);
}
