package com.pbtd.playuser.mapper;

import com.pbtd.playuser.domain.ActivitiesUpgrade;

public interface ActivitiesUpgradeMapper {
	/**
	 * 根据用户ID查询是否领取过流量
	 * @param userId
	 * @return
	 */
	ActivitiesUpgrade queryOneByUserId(String userId);

	/**
	 * 添加升级活动领取奖励信息
	 * @param upgrade
	 * @return
	 */
	int insert(ActivitiesUpgrade upgrade);
}