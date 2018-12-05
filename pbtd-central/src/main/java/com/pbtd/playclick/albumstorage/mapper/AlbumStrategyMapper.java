package com.pbtd.playclick.albumstorage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.integrate.domain.Strategy;
@Mapper
public interface AlbumStrategyMapper {
	
	List<Strategy> findstrategy (Map<String, Object> queryParams);
	//执行非唯一汇聚数据 添加到汇聚库中
	void insertonlyonefalse(Map<String, Object> queryParams);
	//改变当前已汇聚的数据状态
	int  updateonlyonefalsestatus1(Map<String, Object> queryParams);
	int  updateonlyonefalsestatus3(Map<String, Object> queryParams);
	 //执行唯一汇聚数据 添加到汇聚库中
	   void insertonlyone(Map<String, Object> queryParams);
	 //改变当前已汇聚的数据状态
	 int  updateonlyonestatus(Map<String, Object> queryParams);
	 //添加剧集
	 int insertvodalbuminfovideo(Map<String, Object> queryParams);
	 //查询当前临时表中的数据 
	 List<Map<String, Object>> findalbum(Map<String, Object> queryParams);
	 //更改临时表中的拼音 
	 int updatealbumpinyin(Map<String, Object> queryParams);
	 //更新剧集中的自有专辑id
	 int updatevideobycentralid(Map<String, Object> queryParams);
	 //根据id更新拼音
	 List<Map<String, Object>>  findvodalbumid(Map<String, Object> queryParams);
	 //根据id更改表中的拼音 
	 int updatevodalbumpinyinid(Map<String, Object> queryParams);
	 
	//更新数据集数 
	 void updatealbuminfocurrentNum (Map<String, Object> queryParams);//手机
	 
	 void updatevodmappingphone(Map<String, Object> queryParams);//手机
	 // 根据上线映射数据 自动映射
	 List<Map<String,Object>> findvodmapping(Map<String, Object> queryParams);
	 int  updateactors(Map<String, Object> queryParams);//手机
	 
}
