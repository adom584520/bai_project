package com.pbtd.launcher.mapper;

import org.apache.ibatis.annotations.Param;

import com.pbtd.launcher.domain.LauncherUI;

public interface LauncherUIMapper {
	
	/**
	 *	根据分组ID查询已上线的系统UI信息
	 * @param groupId
	 * @param status
	 * @return
	 */
	LauncherUI queryByGroupId(@Param("groupId")Long groupId,@Param("status")Integer status);
}
