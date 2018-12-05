package com.pbtd.playlive.mapper;

import java.util.HashMap;
import java.util.List;

import com.pbtd.playlive.domain.LiveCibnEpg;

public interface LiveCibnEpgMapper {


	LiveCibnEpg selectByPrimaryKey(LiveCibnEpg record);

	List<LiveCibnEpg> selectBytime(HashMap<?, ?> tempBean);

	void truncateTable();

	int insert(LiveCibnEpg record);
	int deleteTimeOut();

}