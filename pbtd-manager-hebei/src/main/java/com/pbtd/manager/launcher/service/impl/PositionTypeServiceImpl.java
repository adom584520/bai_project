package com.pbtd.manager.launcher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.launcher.domain.PositionType;
import com.pbtd.manager.launcher.mapper.PositionTypeMapper;
import com.pbtd.manager.launcher.service.PositionTypeService;

@Service
public class PositionTypeServiceImpl implements PositionTypeService{

	@Autowired
	private  PositionTypeMapper positionTypeMapper;
	
	@Override
	public int add(PositionType positionType) {		
		return positionTypeMapper.add(positionType);
	}

	@Override
	public int deletes(Map<String, Object> ids) {	
		return positionTypeMapper.deletes(ids);
	}

	@Override
	public List<PositionType> find(Map<String, Object> queryParams) {		
		return positionTypeMapper.find(queryParams);
	}

	@Override
	public int generatePosition(Map<String, Object> queryParams) {		
		return positionTypeMapper.generatePosition(queryParams);
	}

	@Override
	public PositionType load(int id) {		
		return positionTypeMapper.load(id);
	}

	@Override
	public int count(Map<String, Object> queryParams) {	
		return positionTypeMapper.count(queryParams);
	}

	@Override
	public int modify(PositionType positionType) {
		return positionTypeMapper.modify(positionType);
	}

}
