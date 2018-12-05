package com.pbtd.playlive.service;

import java.util.List;
import java.util.Map;

import com.pbtd.playlive.domain.LiveVideo;
import com.pbtd.playlive.page.PageResult;

public interface ILiveVideoService {

	/**
	 * 查询所有频道
	 * @return
	 */
	public PageResult<?> querylistallL(Map<String,Object> params);
	public List<LiveVideo> selectbykey(String code);
    
}
