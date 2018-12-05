package com.pbtd.launcher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.launcher.dto.OperationPositionDTO;
import com.pbtd.launcher.mapper.OperationPositionMapper;
import com.pbtd.launcher.service.OperationPositionService;
import com.pbtd.launcher.util.LauncherConstant;

@Service
public class OperationPositionServiceImpl implements OperationPositionService {
	@Autowired
	private OperationPositionMapper operationPositionMapper;

	@Override
	public List<OperationPositionDTO> queryOpListByNavId(Long navId) {
		return operationPositionMapper.queryOpListByNavId(navId, LauncherConstant.UPLINE_STATUS);
	}

}