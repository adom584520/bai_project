package com.pbtd.manager.vod.page.mapper;

import java.util.Map;

public interface JsonInterfaceMapper {
	
	int insertvideointerface(Map<String,Object> map);
	int deletevideointerface(Map<String,Object> map);
	int insertalbuminterface(Map<String,Object> map);
	int updatealbuminterface(Map<String,Object> map);
	int countalbuminterface(Map<String,Object> map);
	int deletealbuminterface(Map<String,Object> map);
}
