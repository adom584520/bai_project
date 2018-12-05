package com.pbtd.playuser.service;

import com.pbtd.playuser.domain.PositionPoint;

public interface PositionPointService {
	/**
	 * 添加运营位打点记录
	 * @param pp
	 */
	void insert(PositionPoint pp);
}
