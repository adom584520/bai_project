package com.pbtd.manager.vod.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pbtd.manager.vod.system.domain.BottomNavigation;
import com.pbtd.manager.vod.system.mapper.BottomNavigationMapper;
import com.pbtd.manager.vod.system.service.face.BottomNavigationService;

@Service
public class BottomNavigationServiceImpl implements BottomNavigationService {

	@Autowired
	private BottomNavigationMapper navigationMapper;

	@Override
	public int add(BottomNavigation bottomNavigation) {
		return navigationMapper.add(bottomNavigation);
	}

	@Override
	public int updateImg(BottomNavigation bottomNavigation) {
		return navigationMapper.updateImg(bottomNavigation);
	}

	@Override
	public int modify(BottomNavigation bottomNavigation) {
		return navigationMapper.modify(bottomNavigation);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		return navigationMapper.deletes(ids);
	}

	@Override
	public List<BottomNavigation> find(Map<String, Object> queryParams) {
		return navigationMapper.find(queryParams);
	}

	@Override
	public List<BottomNavigation> showAll(int start, int limit, Map<String, Object> queryParams) {
		return navigationMapper.showAll(start, limit, queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		return navigationMapper.generatePosition(queryParams);
	}

	@Override
	public BottomNavigation load(int id) {
		return navigationMapper.load(id);
	}

	@Override
	public int count(Map<String, Object> queryParams) {
		return navigationMapper.count(queryParams);
	}



}
