package com.pbtd.launcher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.launcher.dto.OperationPositionDTO;

public interface OperationPositionMapper {
	/**
	 * 根据导航的id查询导航中已上线的运营位
	 * @param navId
	 * @param status
	 * @return
	 */
	List<OperationPositionDTO> queryOpListByNavId(@Param("navId")Long navId,@Param("status")Integer status);
}
