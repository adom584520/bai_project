package com.pbtd.playlive.service;

import java.util.Map;

import com.pbtd.playlive.domain.LiveChannel;
import com.pbtd.playlive.page.PageResult;

public interface ILiveChannelService {

	/**
	 * 查询所有频道
	 * @return
	 */
	public LiveChannel selectLiveChannel(String code);
	public PageResult<?> querylistallLiveChannel(Map<String,Object> params);
	public PageResult<?> queryLiveChannelplay(Map<String,Object> params);
	public PageResult<?> queryLiveChannelplayepg(Map<String,Object> params);
	public PageResult<?> queryLiveChannelplayNowepg(Map<String,Object> params);
	public PageResult<?> queryAllChannle(Map<String,Object> params);
	public PageResult<?> queryOneChannle(Map<String,Object> params);
	public PageResult<?> queryChnPlayEpgInfo(Map<String,Object> params);

    
}
