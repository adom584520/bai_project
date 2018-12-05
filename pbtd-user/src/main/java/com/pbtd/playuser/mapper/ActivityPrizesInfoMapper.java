package com.pbtd.playuser.mapper;

import java.util.List;

import com.pbtd.playuser.domain.ActivityPrizesInfo;
import com.pbtd.playuser.domain.ActivityPrizesOutPut;

public interface ActivityPrizesInfoMapper {

	/**
	 * 根据活动查询出所有的奖品
	 * @param activeName
	 * @return
	 */
	List<ActivityPrizesInfo> queryAll(String activeCode);
	List<ActivityPrizesOutPut> queryForOutPut(String activeCode);
	
	/**
	 * 修改奖品信息
	 * @param info
	 * @return
	 */
	int updateprizeresNum(ActivityPrizesInfo info);
}