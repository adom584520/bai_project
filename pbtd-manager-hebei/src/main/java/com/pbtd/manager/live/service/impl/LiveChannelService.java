package com.pbtd.manager.live.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.mapper.LiveChannelMapper;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveChannelService;

@Service
public class LiveChannelService implements ILiveChannelService {
	@Autowired
	private LiveChannelMapper liveChannelMapper;

	/**
	 * 查出所有商家信息（表格分页）
	 */
	@Override
	public PageResult querylistallLiveChannel() {
		List<LiveChannel> list = liveChannelMapper.selectByPrimaryKey(null);
		return new PageResult((long) list.size(), list);
	}


	@Override
	public int count(Map<String, Object> queryParams) {
		return this.liveChannelMapper.count(queryParams);
	}

	@Override
	public List<LiveChannel> page(int start, int limit, Map<String, Object> queryParams) {
		return this.liveChannelMapper.page(start, limit, queryParams);
	}

	@Override
	public List<LiveChannel> find(Map<String, Object> queryParams) {
		return this.liveChannelMapper.find(queryParams);
	}


	@Override
	public LiveChannel load(int id) {
		return this.liveChannelMapper.load(id);
	}
	@Override
	@Transactional
	public int deletes(Map<String, Object> ids) {
		return this.liveChannelMapper.deletes(ids);
	}

	@Override
	@Transactional
	public int insert(LiveChannel liveChannel) {
		this.liveChannelMapper.insert(liveChannel);
		return liveChannel.getChannelid();
	}
	@Override
	@Transactional
	public int update(LiveChannel liveChannel) {

		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("id", liveChannel.getChannelid());
		//获取原始排序序号 比较是要变大还是变小
		LiveChannel map1=liveChannelMapper.load(liveChannel.getChannelid());
		int currsequence=map1.getDefaultnum();
		int newsequence=liveChannel.getDefaultnum();
		//比对序号是变大还是变小
		if(currsequence>newsequence){
			queryParams.put("plus", "1");
			queryParams.put("minnum", newsequence);
			queryParams.put("maxnum",currsequence);
		}else{
			queryParams.put("minnum", currsequence);
			queryParams.put("maxnum",newsequence);
		}
		liveChannelMapper.updatesequce( queryParams);
	
		return this.liveChannelMapper.update(liveChannel);
	}


	@Override
	public List<LiveChannel> getchannel() {
		return this.liveChannelMapper.getchannel();
	}

}
