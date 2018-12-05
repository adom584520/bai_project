package com.pbtd.playlive.service;

import com.pbtd.playlive.domain.LiveTag;
import com.pbtd.playlive.page.PageResult;

public interface ILiveTagService {

	/**
	 * 查询所有频道
	 * @return
	 */
	public PageResult<?> querylistallLiveTag();
	
    public LiveTag selectbykey(Integer tagId);
}
