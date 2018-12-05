package com.pbtd.playuser.service;

import java.util.List;

import com.pbtd.playuser.domain.PlayHistoryInfo;
import com.pbtd.playuser.page.CollectHistoryQuery;

public interface PlayHistoryInfoPhoneService {
	/**
	 * 添加用户播放记录
	 * @param ph
	 */
	void insert(PlayHistoryInfo ph);

	/**
	 * 修改播放记录的状态
	 * @param id
	 * @return
	 */
	void updateStatus(String userId,String seriesCode);

	/**
	 * 查询播放记录记录
	 * @return
	 */
	List<PlayHistoryInfo> queryAll(CollectHistoryQuery chq);

	/**
	 * 
	 * @param userId
	 * @param seriesCode
	 * @return
	 */
	PlayHistoryInfo queryOne(String userId,Integer seriesCode);

	/**
	 * 修改PlayHistoryInfo
	 * @param ph
	 */
	void update(PlayHistoryInfo ph);

}
