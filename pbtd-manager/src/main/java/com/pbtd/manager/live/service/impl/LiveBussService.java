package com.pbtd.manager.live.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.live.domain.LiveBussInfo;
import com.pbtd.manager.live.mapper.LiveBussInfoMapper;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveBussService;

@Service
public class LiveBussService implements ILiveBussService {
	@Autowired
	private LiveBussInfoMapper liveBussInfoMapper;

	/**
	 * 查出所有商家信息（表格分页）
	 */
	@Override
	public PageResult querylistallLiveBuss() {
		long count = liveBussInfoMapper.querybussinfocount();
		if (count == 0) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<LiveBussInfo> data = liveBussInfoMapper.querybussinfo();
		return new PageResult(count, data);
	}
	
	@Override
	public int count(Map<String, Object> queryParams) {
		return this.liveBussInfoMapper.count(queryParams);
	}

	@Override
	public List<LiveBussInfo> page(int start, int limit, Map<String, Object> queryParams) {
		return this.liveBussInfoMapper.page(start, limit, queryParams);
	}

	@Override
	public List<LiveBussInfo> find(Map<String, Object> queryParams) {
		return this.liveBussInfoMapper.find(queryParams);
	}


	@Override
	public LiveBussInfo load(int id) {
		return this.liveBussInfoMapper.load(id);
	}
	@Override
	@Transactional
	public int deletes(Map<String, Object> ids) {
		return this.liveBussInfoMapper.deletes(ids);
	}

	@Override
	@Transactional
	public int insert(LiveBussInfo liveBussInfo) {
		return	this.liveBussInfoMapper.insert(liveBussInfo);
	}
	   @Override
	    @Transactional
	    public int update(LiveBussInfo liveBussInfo) {
	        return this.liveBussInfoMapper.update(liveBussInfo);
	    }

}
