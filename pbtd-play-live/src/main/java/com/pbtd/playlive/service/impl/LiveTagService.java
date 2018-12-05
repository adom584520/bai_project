package com.pbtd.playlive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playlive.domain.LiveTag;
import com.pbtd.playlive.mapper.LiveTagMapper;
import com.pbtd.playlive.page.DataResult;
import com.pbtd.playlive.page.PageResult;
import com.pbtd.playlive.service.ILiveTagService;

@Service
public class LiveTagService implements ILiveTagService {
	@Autowired
	private LiveTagMapper liveTagMapper;

	/**
	 * 查出所有tv分组信息
	 */
	@Override
	public PageResult<?> querylistallLiveTag() {
		List<LiveTag> list = liveTagMapper.selectByPrimaryKey(null);
		return new PageResult<Object>(new DataResult<List<LiveTag>>((int)list.size(), list));
	}

	@Override
	public LiveTag selectbykey(Integer tagId) {
		return liveTagMapper.load(tagId);
	}

}
