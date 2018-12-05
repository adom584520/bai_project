package com.pbtd.manager.live.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.live.domain.LiveGroup;
import com.pbtd.manager.live.mapper.LiveBussInfoMapper;
import com.pbtd.manager.live.mapper.LiveGroupMapper;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveGroupService;

@Service
public class LiveGroupService implements ILiveGroupService {
	@Autowired
	private LiveGroupMapper liveGroupMapper;
	@Autowired
	private LiveBussInfoMapper liveBussInfoMapper;
	/**
	 * 查出所有商家信息（表格分页）
	 */
	@Override
	public PageResult querylistallLiveGroup() {
		List<LiveGroup> list = liveGroupMapper.selectByPrimaryKey(null);
		return new PageResult((long) list.size(), list);
	}


	@Override
	public int count(Map<String, Object> queryParams) {
		return this.liveGroupMapper.count(queryParams);
	}

	@Override
	public List<LiveGroup> page(int start, int limit, Map<String, Object> queryParams) {
		return this.liveGroupMapper.page(start, limit, queryParams);
	}

	@Override
	public List<LiveGroup> find(Map<String, Object> queryParams) {
		return this.liveGroupMapper.find(queryParams);
	}


	@Override
	public LiveGroup load(int id) {
		return this.liveGroupMapper.load(id);
	}
	@Override
	@Transactional
	public int deletes(Map<String, Object> ids) {
		return this.liveGroupMapper.deletes(ids);
	}

	@Override
	@Transactional
	public int insert(LiveGroup liveGroup) {
		this.liveGroupMapper.insert(liveGroup);
		return liveGroup.getGroupid();
	}
	   @Override
	    @Transactional
	    public int update(LiveGroup liveGroup) {
	        return this.liveGroupMapper.update(liveGroup);
	    }


	@Override
	public List<LiveGroup> getgroup(String id) {
		int bussid = liveBussInfoMapper.selectByValue(id).getBussid();
		return this.liveGroupMapper.getgroup(bussid);
	}

}
