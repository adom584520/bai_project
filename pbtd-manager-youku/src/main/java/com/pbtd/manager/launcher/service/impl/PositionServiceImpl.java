package com.pbtd.manager.launcher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.launcher.domain.Position;
import com.pbtd.manager.launcher.mapper.PositionMapper;
import com.pbtd.manager.launcher.service.PositionService;


@Service
public class PositionServiceImpl implements PositionService{
	
	@Autowired 
	private PositionMapper positionMapper;

	@Override
	public int add(Position position) {
		return positionMapper.add(position);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
		return positionMapper.deletes(ids);
	}

	@Override
	public int modify(Position position) {
		

		return positionMapper.modify(position);
	}

	@Override
	public List<Position> find(Map<String, Object> queryParams) {
	
		return positionMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {
		return positionMapper.generatePosition(queryParams);
	}

	@Override
	public Position load(int id) {
		return positionMapper.load(id);
	}

	@Override
	public int count(Map<String, Object> queryParams) {
		return positionMapper.count(queryParams);
	}

	@Override
	public List<Map<String, Object>> queryType(Map<String,Object> params) {
			return positionMapper.queryType(params);
	}

}
