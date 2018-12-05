package com.pbtd.launcher.service;

import java.util.List;

import com.pbtd.launcher.dto.EasyNavigationDTO;
import com.pbtd.launcher.dto.NavigationDTO;

public interface NavigationService {
	/**
	 * 根据分组ID查询所有上线的导航位
	 * @param groupId
	 * @return
	 */
	List<NavigationDTO> queryNavListByGroupId(Long groupId);
	
	/**
	 * 根据groupId查询导航列表简单信息
	 * @param groupId
	 * @return
	 */
	List<EasyNavigationDTO> queryEasyAllByGroupId(Long groupId);

}
