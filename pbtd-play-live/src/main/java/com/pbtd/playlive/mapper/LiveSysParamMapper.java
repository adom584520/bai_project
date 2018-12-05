package com.pbtd.playlive.mapper;
	
import java.util.List;

import com.pbtd.playlive.domain.LiveSysParam;

public interface LiveSysParamMapper {

    LiveSysParam selectByPrimaryKey(Integer paramid);
    List<LiveSysParam>  selectAll();

    
}