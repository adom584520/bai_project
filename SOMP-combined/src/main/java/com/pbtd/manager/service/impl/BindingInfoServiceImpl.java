package com.pbtd.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.domain.BindingInfo;
import com.pbtd.manager.mapper.BindingInfoMapper;
import com.pbtd.manager.service.BindingInfoService;

@Service
public class BindingInfoServiceImpl implements BindingInfoService {
	@Autowired
	private BindingInfoMapper bindingInfoMapper;

	@Override
	public BindingInfo queryByTvId(String tvId) {
		return bindingInfoMapper.queryByTvId(tvId);
	}

	@Override
	public BindingInfo queryByPhoneId(String phoneId) {
		return bindingInfoMapper.queryByPhoneId(phoneId);
	}
}
