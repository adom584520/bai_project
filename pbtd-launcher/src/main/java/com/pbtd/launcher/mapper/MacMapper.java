package com.pbtd.launcher.mapper;

import com.pbtd.launcher.domain.Mac;

public interface MacMapper {
	/**
	 * 通过macName获取mac
	 * @param macName
	 * @return
	 */
	Mac queryByMac(String macName);
}
