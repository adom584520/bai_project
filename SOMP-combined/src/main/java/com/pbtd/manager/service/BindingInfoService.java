package com.pbtd.manager.service;

import com.pbtd.manager.domain.BindingInfo;

public interface BindingInfoService {
	BindingInfo queryByTvId(String tvId);

	BindingInfo queryByPhoneId(String phoneId);
}
