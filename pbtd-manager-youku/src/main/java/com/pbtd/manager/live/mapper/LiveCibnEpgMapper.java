package com.pbtd.manager.live.mapper;

import java.util.List;

import com.pbtd.manager.live.domain.LiveCibnEpg;

public interface LiveCibnEpgMapper {

    int insert(LiveCibnEpg record);

    int insertSelective(LiveCibnEpg record);

    LiveCibnEpg selectByPrimaryKey(LiveCibnEpg record);

    int deleteTimeOut();
    
    List<LiveCibnEpg> getcibnepg(String startdate);
}