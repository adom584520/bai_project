package com.pbtd.playuser.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playuser.domain.PositionPoint;
import com.pbtd.playuser.mapper.PositionPointMapper;
import com.pbtd.playuser.service.PositionPointService;

@Service
public class PositionPointServiceImpl implements PositionPointService {
	@Autowired
	private PositionPointMapper positionPointMapper;

	@Override
	public void insert(PositionPoint pp) {
		pp.setCreateTime(new Date());
		positionPointMapper.insert(pp);
	}
}