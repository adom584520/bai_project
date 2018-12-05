package com.pbtd.manager.live.mapper;

import com.pbtd.manager.live.domain.LiveChannelSource;

public interface LiveChannelSourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiveChannelSource record);

    int insertSelective(LiveChannelSource record);

    LiveChannelSource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiveChannelSource record);

    int updateByPrimaryKey(LiveChannelSource record);
}