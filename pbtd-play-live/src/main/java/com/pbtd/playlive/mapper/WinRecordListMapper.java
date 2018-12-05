package com.pbtd.playlive.mapper;

import com.pbtd.playlive.domain.WinRecordList;

public interface WinRecordListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WinRecordList record);

    int insertSelective(WinRecordList record);

    WinRecordList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WinRecordList record);

    int updateByPrimaryKeyWithBLOBs(WinRecordList record);

    int updateByPrimaryKey(WinRecordList record);
}