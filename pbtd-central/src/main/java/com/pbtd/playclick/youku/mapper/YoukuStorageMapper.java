package com.pbtd.playclick.youku.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YoukuStorageMapper {
	//优酷根据id入库
	void insertisStoragevideoyoukid(Map<String, Object> queryParams);//新增节目
	//优酷根据id映射标签关系
	void updatevodmappingid(Map<String, Object> queryParams);
	// 根据id入库数据
	void insertsisStorageid(Map<String, Object> queryParams);
	//根据id更新节目演员 
	int  updateactorsid(Map<String, Object> queryParams);
	int  updatealbuminfocurrentNum (Map<String, Object> queryParams);
	int updatevideoisStorageyouku (Map<String, Object> queryParams);
	//更改优酷地区年代标签 
	int updatealbumareaYear(Map<String, Object> queryParams);



	//自动更新优酷数据入库youk_vod_albuminfo_strategy
	int deleteyoukualbum_strategy(Map<String, Object> queryParams);
	int deleteyoukualbumvideo_strategy(Map<String, Object> queryParams);
	int  insertyoukualbum_strategy(Map<String, Object> queryParams);
	int insertyoukualbumvideo_strategy(Map<String, Object> queryParams);
	//更新手机标签映射关系
	int updatevodmapping_strategy(Map<String, Object> queryParams);
	//更新节目演员 
	int  updateactors_strategy(Map<String, Object> queryParams);
	//查询待更新拼音的数据 
	List<Map<String, Object>> findvodalbum_strategy(Map<String, Object> queryParams);
	//更改拼音
	int updatevodalbumpinyin_strategy(Map<String, Object> queryParams);
	// 开始入正式库 专辑
	int insertyoukualbum(Map<String, Object>queryParams);
	//更新入库状态
	int updateisStorageyouku(Map<String, Object> queryParams);
	//更改最新剧集数
	int updatealbumcurnum(Map<String, Object> queryParams);
	//剧集入正式库
	int insertyoukualbumvideo(Map<String, Object> queryParams);
	//剧集填充己方专辑id
	int updatealbuminfovideobycentral(Map<String, Object> queryParams);


	//查询地区年代标签列表
	List<Map<String, Object>> findyouk_areaYear(Map<String, Object> queryParams);
	//更改地区年代标签关系
	int updateyouk_areaYear(Map<String, Object> queryParams);
	
	
	
	 //自动下发  更新下发状态
	 int updatevod_albumissue(Map<String, Object> queryParams);
	 
	int  loadvodalbum(Map<String, Object> queryParams);


}
