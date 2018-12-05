package com.pbtd.manager.launcher.mapper;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.launcher.domain.LauncherVersion;

public interface LauncherVersionMapper {
	/**
	 * 根据类型查询版本号
	 * @param type
	 * @return
	 */
	LauncherVersion queryByType(@Param("groupId")Long groupId,@Param("type")Integer type);

	/**
	 * 添加版本信息
	 * @param version
	 */
	Integer insert(LauncherVersion version);

	/**
	 * 修改版本信息
	 * @param version
	 */
	Integer update(LauncherVersion version);
}
