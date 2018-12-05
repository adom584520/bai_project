package com.pbtd.playlive.mapper;
	
import com.pbtd.playlive.domain.LiveChannelSource;

public interface LiveChannelSourceMapper {
	
    LiveChannelSource selectByPrimaryKey(String code);

}