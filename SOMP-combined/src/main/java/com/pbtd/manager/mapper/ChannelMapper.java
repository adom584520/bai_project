package com.pbtd.manager.mapper;

import java.util.List;

import com.pbtd.manager.domain.Channel;
import com.pbtd.manager.query.ChannelQueryObject;

public interface ChannelMapper {
	Channel queryByChnCode(String chnCode);
	
	/**
	 * 查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(ChannelQueryObject qo);

	/**
	 * 查询总记录
	 * @param qo
	 * @return
	 */
	List<Channel> queryList(ChannelQueryObject qo);
}
