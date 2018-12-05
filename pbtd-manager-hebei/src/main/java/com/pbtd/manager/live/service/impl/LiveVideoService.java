package com.pbtd.manager.live.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.live.domain.LiveVideo;
import com.pbtd.manager.live.mapper.LiveVideoMapper;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveVideoService;

@Service
public class LiveVideoService implements ILiveVideoService {
	@Autowired
	private LiveVideoMapper liveVideoMapper;

	
	
	
	/**
	 * 
	 */
	@Override
	public PageResult querylistallLiveVideo() {
		List<LiveVideo> list = liveVideoMapper.selectByPrimaryKey(null);
		return new PageResult((long) list.size(), list);
	}


	@Override
	public int count(Map<String, Object> queryParams) {
		return this.liveVideoMapper.count(queryParams);
	}

	@Override
	public List<LiveVideo> page(int start, int limit, Map<String, Object> queryParams) {
		return this.liveVideoMapper.page(start, limit, queryParams);
	}

	@Override
	public List<LiveVideo> find(Map<String, Object> queryParams) {
		return this.liveVideoMapper.find(queryParams);
	}


	@Override
	public LiveVideo load(int id) {
		return this.liveVideoMapper.load(id);
	}
	@Override
	@Transactional
	public int deletes(Map<String, Object> ids) {
		return this.liveVideoMapper.deletes(ids);
	}

	@Override
	@Transactional
	public int insert(LiveVideo liveVideo) {
		this.liveVideoMapper.insert(liveVideo);
		return liveVideo.getVideoid();
	}
	   @Override
	    @Transactional
	    public int update(LiveVideo liveVideo) {
	        return this.liveVideoMapper.update(liveVideo);
	    }


	@Override
	public List<LiveVideo> getvideo() {
		return  this.liveVideoMapper.getvideo();
	}

}
