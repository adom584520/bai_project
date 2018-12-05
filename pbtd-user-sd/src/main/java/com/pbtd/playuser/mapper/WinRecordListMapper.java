package com.pbtd.playuser.mapper;

import java.util.HashMap;

import com.pbtd.playuser.domain.WinRecordList;

public interface WinRecordListMapper {

	
    int insert(WinRecordList record);

    int update(HashMap<String, Object> params);
    
    WinRecordList  select(HashMap<String, Object> params);

}