package com.pbtd.playclick.guoguang.service;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

 
public interface ICibnStorageService {

	//查询更新数据 并添加进临时表
	 int  albumStorage(Map<String,Object> map);
	 
		//根据id查询数据 并入库
	 int  albumStorageid(Map<String,Object> map);
	
}
