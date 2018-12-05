package com.pbtd.manager.live.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.live.domain.LiveTag;
import com.pbtd.manager.live.mapper.LiveTagMapper;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveTagService;

@Service
public class LiveTagService implements ILiveTagService {
	@Autowired
	private LiveTagMapper liveTagMapper;

	/**
	 * 查出所有商家信息（表格分页）
	 */
	@Override
	public PageResult querylistallLiveTag() {
		List<LiveTag> list = liveTagMapper.selectByPrimaryKey(null);
		return new PageResult((long) list.size(), list);
	}


	@Override
	public int count(Map<String, Object> queryParams) {
		return this.liveTagMapper.count(queryParams);
	}

	@Override
	public List<LiveTag> page(int start, int limit, Map<String, Object> queryParams) {
		return this.liveTagMapper.page(start, limit, queryParams);
	}

	@Override
	public List<LiveTag> find(Map<String, Object> queryParams) {
		return this.liveTagMapper.find(queryParams);
	}


	@Override
	public LiveTag load(int id) {
		return this.liveTagMapper.load(id);
	}
	@Override
	@Transactional
	public int deletes(Map<String, Object> ids) {
		return this.liveTagMapper.deletes(ids);
	}

	@Override
	@Transactional
	public int insert(LiveTag liveGroup) {
		this.liveTagMapper.insert(liveGroup);
		return liveGroup.getTagid();
	}
	   @Override
	    @Transactional
	    public int update(LiveTag liveGroup) {
	        return this.liveTagMapper.update(liveGroup);
	    }


	@Override
	public List<LiveTag> gettag() {
		return this.liveTagMapper.gettag();
	}

}
