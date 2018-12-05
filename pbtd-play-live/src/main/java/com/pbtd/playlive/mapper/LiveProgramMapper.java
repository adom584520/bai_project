package com.pbtd.playlive.mapper;

import java.util.HashMap;
import java.util.List;

import com.pbtd.playlive.domain.LiveProgram;

public interface LiveProgramMapper {
	LiveProgram selectByPrimaryKey(LiveProgram record);
	List<LiveProgram> selectProList(HashMap<?, ?> tempBean);
	void truncateTable();
	int insert(LiveProgram record);
    int deleteTimeOut();

}