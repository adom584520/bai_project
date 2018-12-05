package com.pbtd.launcher.service;

import java.util.List;

import com.pbtd.launcher.dto.OperationPositionDTO;

public interface OperationPositionService {
	/**
	 * 根据导航的id查询导航中已上线的运营位
	 * @param navId
	 * @return
	 */
	List<OperationPositionDTO> queryOpListByNavId(Long navId);
}
