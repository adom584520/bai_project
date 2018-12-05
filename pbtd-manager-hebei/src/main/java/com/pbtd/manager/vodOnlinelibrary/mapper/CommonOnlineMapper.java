package com.pbtd.manager.vodOnlinelibrary.mapper;

import java.util.Map;

public interface CommonOnlineMapper {
	int  insertcorner(Map<String,Object> map);//添加角标
	int  deletescorner(Map<String,Object> map);//删除角标
	int  insertCollectfeesbag(Map<String,Object> map);//添加付费包
	int  deletesCollectfeesbag(Map<String,Object> map);//删除付费包
	int  insertactors(Map<String,Object> map);//添加演员
	int  deletesactors(Map<String,Object> map);//删除演员
	int  deletesmasterplateSon(Map<String,Object> map);//删除模版详情
	int  insertmasterplateSon(Map<String,Object> map);//删除模版详情
	int  deletesSysParam(Map<String,Object> map);//删除系统参数
	int  insertSysParam(Map<String,Object> map);//添加系统参数
}
