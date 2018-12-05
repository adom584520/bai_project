package com.pbtd.manager.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pbtd.manager.system.domain.UserKeep;
import com.pbtd.manager.user.page.UpgradeQueryObject;
import com.pbtd.manager.util.PageResult;

public interface SysCountService {

	PageResult queryList(UpgradeQueryObject qo);

	List<Map<String,String>> page(Map<String,Object> queryParams);

	List<Map<String,Object>> find(Map<String,Object> queryParams);
	List<Map<String,Object>> seriespagefind(Map<String,Object> queryParams);
	List<Map<String,Object>> channelpagefind(Map<String,Object> queryParams);
	List<Map<String,Object>> mobilepagefind(Map<String,Object> queryParams);
	List<Map<String,Object>> mobileseriespagefind(Map<String,Object> queryParams);
	List<Map<String,Object>> userkeeppagefind(Map<String,Object> queryParams);
	int count(Map<String,Object> queryParams);
	int seriespagecount(Map<String,Object> queryParams);
	int channelpagecount(Map<String,Object> queryParams);
	int mobilepagecount(Map<String,Object> queryParams);
	int mobileseriespagecount(Map<String,Object> queryParams);
	
	//定时
	void usercount(Date now);
	void seriescount(Date now);
	
	List<Map<String,Object>> findallchannel();

    List<UserKeep> getUserKeep(Map<String, Object> queryParams);

	int insertUserKeep(Map<String, Object> queryParams);
}
