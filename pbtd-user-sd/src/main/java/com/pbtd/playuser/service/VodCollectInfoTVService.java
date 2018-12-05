package com.pbtd.playuser.service;

import java.util.List;

import com.pbtd.playuser.domain.VodCollectInfo;
import com.pbtd.playuser.page.CollectHistoryQuery;

public interface VodCollectInfoTVService {

	/**
	 * 添加用户收藏记录
	 * @param collectInfo
	 */
	void insert(VodCollectInfo collectInfo);

	/**
	 * 删除收藏
	 * @param userId
	 * @param seriesCode
	 * @return
	 */
	void delete(String mac,String seriesCode);

	/**
	 * 查询收藏记录
	 * @return
	 */
	List<VodCollectInfo> queryAll(CollectHistoryQuery chq);
	
	/**
	 * 是否收藏接口
	 * @param userId
	 * @param seriesCode
	 * @return
	 */
	boolean queryIsCollect(String mac,Integer seriesCode);
	
	/**
	 * 修改VodCollectInfo
	 * @param ph
	 */
	void update(VodCollectInfo collectInfo);

}
