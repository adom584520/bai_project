package com.pbtd.playclick.youku.service.face;

import java.util.Map;

public interface IYoukuStorageService {

	 //优酷根据id入库
	void insertsisStorageid(Map<String, Object> queryParams);
	 //优酷根据id映射标签关系
	 void updatevodmappingid(Map<String, Object> queryParams);
	 
	 
	 //自动更新优酷数据入库
	 int saveStrategyyouku(Map<String, Object> queryParams);
	 
	 //自动下发  更新下发状态
	 int updatevod_albumissue(Map<String, Object> queryParams);
	 
	 
}
