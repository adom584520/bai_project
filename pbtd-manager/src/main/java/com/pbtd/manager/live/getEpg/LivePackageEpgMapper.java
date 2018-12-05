package com.pbtd.manager.live.getEpg;

import java.util.List;

import com.pbtd.manager.live.domain.getEpg.LivePackageEpg;


public interface LivePackageEpgMapper {

	/**
	 * 查询所有频道
	 * @param tempBean
	 * @param row
	 * @return
	 */
	List<LivePackageEpg> select();

}
