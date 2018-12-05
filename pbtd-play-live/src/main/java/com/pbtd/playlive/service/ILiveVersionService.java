package com.pbtd.playlive.service;

import java.util.Map;

import com.pbtd.playlive.page.PageResult;

public interface ILiveVersionService {

	/**
	 * 查询所有频道
	 * @return
	 */
	public PageResult<?> querylistallL(Map<String,Object> params);
    
}
