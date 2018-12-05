package com.pbtd.manager.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.domain.Channel;
import com.pbtd.manager.mapper.ChannelMapper;
import com.pbtd.manager.query.ChannelQueryObject;
import com.pbtd.manager.service.ChannelService;
import com.pbtd.manager.util.PageResult;

@Service
public class ChannelServiceImpl implements ChannelService {
	@Autowired
	private ChannelMapper channelMapper;

	@Override
	public Channel queryByChnCode(String chnCode) {
		return channelMapper.queryByChnCode(chnCode);
	}

	@Override
	public PageResult queryList(ChannelQueryObject qo) {
		Long count = channelMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<Channel> data = channelMapper.queryList(qo);
		return new PageResult(count, data);
	}
}
