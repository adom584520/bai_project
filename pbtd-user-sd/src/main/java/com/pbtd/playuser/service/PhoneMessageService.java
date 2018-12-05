package com.pbtd.playuser.service;

import com.pbtd.playuser.domain.PhoneMessage;

public interface PhoneMessageService {
	void updateIOSAudit(Integer status);

	PhoneMessage iOSAudit(Integer type);
}
