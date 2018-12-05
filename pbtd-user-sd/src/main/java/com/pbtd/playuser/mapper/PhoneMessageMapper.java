package com.pbtd.playuser.mapper;

import org.apache.ibatis.annotations.Param;

import com.pbtd.playuser.domain.PhoneMessage;

public interface PhoneMessageMapper {
	void updateIOSAudit(@Param("type")Integer type,@Param("status")Integer status);
	PhoneMessage iOSAudit(Integer type);
}
