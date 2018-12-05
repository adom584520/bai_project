package com.pbtd.manager.mapper;

import com.pbtd.manager.domain.BindingInfo;

public interface BindingInfoMapper {
	BindingInfo queryByTvId(String tvId);

	BindingInfo queryByPhoneId(String phoneId);
}
