package com.pbtd.playclick.heyi.service;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IGuttvStorageService {
	//查询更新数据 并添加进临时表
	 int   albumStorage(Map<String,Object> map);
		//根据id查询数据 并入库
	 int  albumStorageid(Map<String,Object> map);
}
