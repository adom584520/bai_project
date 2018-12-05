package com.pbtd.manager.live.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.live.domain.LiveVersionBussInfo;
import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.mapper.LiveVersionBussInfoMapper;
import com.pbtd.manager.live.mapper.LiveChannelMapper;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveVersionBussInfoService;

@Service
public class LiveVersionBussInfoService implements ILiveVersionBussInfoService{
	@Autowired
	private LiveVersionBussInfoMapper liveVersionBussInfoMapper;
	/**
	 * 查出所有商家信息（表格分页）
	 */
	@Override
	public PageResult querylistallLiveVersionBussInfo() {
		List<LiveVersionBussInfo> list = liveVersionBussInfoMapper.selectByPrimaryKey(null);
		return new PageResult((long) list.size(), list);
	}


	@Override
	public int count(Map<String, Object> queryParams) {
		return this.liveVersionBussInfoMapper.count(queryParams);
	}

	@Override
	public List<LiveVersionBussInfo> page(int start, int limit, Map<String, Object> queryParams) {
		return this.liveVersionBussInfoMapper.page(start, limit, queryParams);
	}

	@Override
	public List<LiveVersionBussInfo> find(Map<String, Object> queryParams) {
		return this.liveVersionBussInfoMapper.find(queryParams);
	}
	
	@Override
	public List<LiveChannel> finds(Map<String, Object> queryParams) {
		return this.liveVersionBussInfoMapper.finds(queryParams);
	}


	@Override
	public LiveVersionBussInfo load(int id) {
		return this.liveVersionBussInfoMapper.load(id);
	}
	@Override
	@Transactional
	public int deletes(Map<String, Object> ids) {
		return this.liveVersionBussInfoMapper.deletes(ids);
	}
	@Override
	@Transactional
	public int creates(Map<String, Object> ids) {
		return this.liveVersionBussInfoMapper.deletes(ids);
	}

	@Override
	@Transactional
	public int insert(LiveVersionBussInfo liveVersionBussInfo) {
		this.liveVersionBussInfoMapper.insert(liveVersionBussInfo);
		return liveVersionBussInfo.getId();
	}
	   @Override
	    @Transactional
	    public int update(LiveVersionBussInfo liveVersionBussInfo) {
	        return this.liveVersionBussInfoMapper.update(liveVersionBussInfo);
	    }

}
