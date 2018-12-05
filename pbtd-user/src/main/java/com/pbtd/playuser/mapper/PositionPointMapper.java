package com.pbtd.playuser.mapper;

import com.pbtd.playuser.domain.PositionPoint;

public interface PositionPointMapper {
	/**
	 * 添加打点记录
	 * @param pp
	 * @return
	 */
	int insert(PositionPoint pp);
}
