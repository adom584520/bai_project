package com.pbtd.manager.live.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.live.domain.LiveVersion;
import com.pbtd.manager.live.mapper.LiveBussInfoMapper;
import com.pbtd.manager.live.mapper.LiveSysParamMapper;
import com.pbtd.manager.live.mapper.LiveVersionMapper;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveVersionService;

@Service
public class LiveVersionService implements ILiveVersionService {
	@Autowired
	private LiveVersionMapper liveVersionMapper;
	@Autowired
	private LiveBussInfoMapper liveBussInfoMapper;
	
	
	
	/**
	 * 
	 */
	@Override
	public PageResult querylistallLiveVersion() {
		List<LiveVersion> list = liveVersionMapper.selectByPrimaryKey(null);
		return new PageResult((long) list.size(), list);
	}


	@Override
	public int count(Map<String, Object> queryParams) {
		return this.liveVersionMapper.count(queryParams);
	}

	@Override
	public List<LiveVersion> page(int start, int limit, Map<String, Object> queryParams) {
		return this.liveVersionMapper.page(start, limit, queryParams);
	}

	@Override
	public List<LiveVersion> find(Map<String, Object> queryParams) {
		return this.liveVersionMapper.find(queryParams);
	}


	@Override
	public LiveVersion load(int id) {
		return this.liveVersionMapper.load(id);
	}
	@Override
	@Transactional
	public int deletes(Map<String, Object> ids) {
		return this.liveVersionMapper.deletes(ids);
	}

	@Override
	@Transactional
	public int insert(LiveVersion liveVersion) {
		this.liveVersionMapper.insert(liveVersion);
		return liveVersion.getVersionid();
	}
	   @Override
	    @Transactional
	    public int update(LiveVersion liveVersion) {
	        return this.liveVersionMapper.update(liveVersion);
	    }


	@Override
	public LiveVersion getversion(String id) {
		int bussid = liveBussInfoMapper.selectByValue(id).getBussid();
		return this.liveVersionMapper.getversion(bussid);
	}

}
