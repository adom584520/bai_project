package com.pbtd.manager.service;

import com.pbtd.manager.domain.Channel;
import com.pbtd.manager.query.ChannelQueryObject;
import com.pbtd.manager.util.PageResult;

public interface ChannelService {
	Channel queryByChnCode(String chnCode);
	
	PageResult queryList(ChannelQueryObject qo);
}
