package com.pbtd.playuser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playuser.domain.PhoneMessage;
import com.pbtd.playuser.mapper.PhoneMessageMapper;
import com.pbtd.playuser.service.PhoneMessageService;

@Service
public class PhoneMessageServiceImpl implements PhoneMessageService {
	@Autowired
	private PhoneMessageMapper phoneMessageMapper;

	@Override
	public void updateIOSAudit(Integer status) {
		phoneMessageMapper.updateIOSAudit(PhoneMessage.TYPE_IOS, status);
	}

	@Override
	public PhoneMessage iOSAudit(Integer type) {
		return phoneMessageMapper.iOSAudit(PhoneMessage.TYPE_IOS);
	}
}
