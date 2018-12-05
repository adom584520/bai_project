package com.pbtd.launcher.mapper;

import org.apache.ibatis.annotations.Param;

import com.pbtd.launcher.dto.UpgradeDTO;

public interface UpgradeMapper {
	/**
	 * 通过APK类型和版本状态查询版本信息
	 * @param type
	 * @param status
	 * @return
	 */
	UpgradeDTO queryByTypeStatus(@Param("type")Integer type,@Param("status")Integer status);

}
