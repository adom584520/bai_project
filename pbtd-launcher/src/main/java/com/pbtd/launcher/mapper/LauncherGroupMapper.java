package com.pbtd.launcher.mapper;

import java.util.List;

import com.pbtd.launcher.dto.LauncherGroupDTO;

public interface LauncherGroupMapper {

	/**
	 * 查询所有分组ID和分组名称
	 * @return
	 */
	List<LauncherGroupDTO> queryAllGroupIdAndName();
}
