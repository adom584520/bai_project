package com.pbtd.manager.live.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.live.domain.LiveCibnEpg;

public interface LiveCibnEpgMapper {

    int insert(LiveCibnEpg record);
    int delete(Map<String, Object> map);

    int insertSelective(LiveCibnEpg record);

    LiveCibnEpg selectByPrimaryKey(LiveCibnEpg record);
    
    List<LiveCibnEpg> getcibnepg(String startdate);

}