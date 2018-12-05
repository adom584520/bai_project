package com.pbtd.launcher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.launcher.dto.EasyNavigationDTO;
import com.pbtd.launcher.dto.LauncherGroupDTO;
import com.pbtd.launcher.mapper.LauncherGroupMapper;
import com.pbtd.launcher.service.LauncherGroupService;
import com.pbtd.launcher.service.NavigationService;

@Service
public class LauncherGroupServiceImpl implements LauncherGroupService {
	@Autowired
	private LauncherGroupMapper launcherGroupMapper;
	@Autowired
	private NavigationService navigationService;

	@Override
	public List<LauncherGroupDTO> queryAllGroupIdAndName() {
		List<LauncherGroupDTO> groupList = launcherGroupMapper.queryAllGroupIdAndName();
		for (int i = 0; i < groupList.size(); i++) {
			LauncherGroupDTO group = groupList.get(i);
			List<EasyNavigationDTO> navList = navigationService.queryEasyAllByGroupId(group.getGroupId());
			if (navList!=null) {
				group.setNavList(navList);
			}
		}
		return groupList;
	}

}
