package com.pbtd.manager.live.getEpg;

import java.util.List;

import com.pbtd.manager.live.domain.getEpg.LiveVideoEpg;

public interface LiveVideoEpgMapper  {
	/**
	 * 查询节目包
	 * @param tempBean
	 * @param row
	 * @return
	 */
	List<LiveVideoEpg> select();
}
