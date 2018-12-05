package com.pbtd.launcher.mapper;

import org.apache.ibatis.annotations.Param;

import com.pbtd.launcher.domain.Advertisement;

public interface AdvertisementMapper {
	/**
	 * 通过分组ID和广告类型获取已上线的开机广告信息
	 * @param groupId
	 * @return
	 */
	Advertisement queryStartAdvByGroupId(@Param("groupId")Long groupId,@Param("type")Integer type,@Param("status")Integer status);
}
