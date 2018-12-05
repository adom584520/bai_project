package com.pbtd.playlive.service;

import java.util.Map;

import com.pbtd.playlive.page.PageResult;


public interface ILivePackageService {
	
	/**
	 * 下拉框
	 * @return
	 */
	
	public PageResult<?> querylistallPackage(Map<String,Object> params);
	
}
