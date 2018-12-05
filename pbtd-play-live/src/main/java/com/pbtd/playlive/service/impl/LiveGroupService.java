package com.pbtd.playlive.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.playlive.domain.LiveGroup;
import com.pbtd.playlive.mapper.LiveGroupMapper;
import com.pbtd.playlive.page.DataResult;
import com.pbtd.playlive.page.PageResult;
import com.pbtd.playlive.service.ILiveGroupService;

@Service
public class LiveGroupService implements ILiveGroupService {
	@Autowired
	private LiveGroupMapper liveGroupMapper;

	/**
	 * 查出所有商家信息（表格分页）
	 */
	@Override
	public PageResult querylistallLiveGroup() {
		List<LiveGroup> list = liveGroupMapper.selectByPrimaryKey(null);
		return new PageResult(new DataResult((int)list.size(), list));
	}

}
