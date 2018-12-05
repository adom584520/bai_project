package com.pbtd.launcher.mapper;

import org.apache.ibatis.annotations.Param;

import com.pbtd.launcher.domain.LauncherVersion;

public interface LauncherVersionMapper {
	/**
	 * 根据类型查询版本号
	 * @param type
	 * @return
	 */
	LauncherVersion queryByType(@Param("groupId")Long groupId,@Param("type")Integer type);
}
