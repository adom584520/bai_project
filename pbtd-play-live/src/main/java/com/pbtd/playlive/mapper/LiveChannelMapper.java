package com.pbtd.playlive.mapper;

import java.util.List;

import com.pbtd.playlive.domain.LiveChannel;

public interface LiveChannelMapper {
	
	List<LiveChannel> selectByKey(LiveChannel livechannel);
	LiveChannel select(String chncode);
	LiveChannel selectoldcode(String oldchncode);
	
	void truncateTable();
	int insert(LiveChannel record);
	int delete(String chncode);

}