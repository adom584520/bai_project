package com.pbtd.launcher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.launcher.dto.EasyNavigationDTO;
import com.pbtd.launcher.dto.NavigationDTO;

public interface NavigationMapper {
	/**
	 * 根据分组ID查询所有上线的导航位
	 * @param groupId
	 * @return
	 */
	List<NavigationDTO> queryNavListByGroupId(@Param("groupId")Long groupId,@Param("status")Integer status);

	/**
	 * 根据groupId查询导航列表简单信息
	 * @param groupId
	 * @return
	 */
	List<EasyNavigationDTO> queryEasyAllByGroupId(Long groupId);
}
