package com.pbtd.manager.live.mapper;

import com.pbtd.manager.live.domain.LiveSysParam;

public interface LiveSysParamMapper {
    int deleteByPrimaryKey(Integer paramid);

    int insert(LiveSysParam record);

    int insertSelective(LiveSysParam record);

    LiveSysParam selectByPrimaryKey(Integer paramid);

    int updateByPrimaryKeySelective(LiveSysParam record);

    int updateByPrimaryKey(LiveSysParam record);
}