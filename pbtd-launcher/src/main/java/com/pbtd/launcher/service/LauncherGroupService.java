package com.pbtd.launcher.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pbtd.launcher.dto.LauncherGroupDTO;

@Service
public interface LauncherGroupService {

	/**
	 * 查询所有分组ID和分组名称
	 * @return
	 */
	List<LauncherGroupDTO> queryAllGroupIdAndName();

}
