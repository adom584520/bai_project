package com.pbtd.launcher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.launcher.dto.EasyNavigationDTO;
import com.pbtd.launcher.dto.NavigationDTO;
import com.pbtd.launcher.mapper.NavigationMapper;
import com.pbtd.launcher.service.NavigationService;
import com.pbtd.launcher.util.LauncherConstant;

@Service
public class NavigationServiceImpl implements NavigationService {
	@Autowired
	private NavigationMapper navigationMapper;

	@Override
	public List<NavigationDTO> queryNavListByGroupId(Long groupId) {
		return navigationMapper.queryNavListByGroupId(groupId, LauncherConstant.UPLINE_STATUS);
	}

	@Override
	public List<EasyNavigationDTO> queryEasyAllByGroupId(Long groupId) {
		return navigationMapper.queryEasyAllByGroupId(groupId);
	}

}