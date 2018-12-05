package com.pbtd.playclick.guoguang.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.guoguang.domain.GgAlbumsinfo;

@Mapper
public interface CibnStorageMapper {

	//查询更新数据 并添加进临时表
	 int  inseralbumStorage(Map<String,Object> map);
	 int  inseralbumvideoStorage(Map<String,Object> map);
	 int  deletealbumStorage(Map<String,Object> map);
	 int  deletealbumvideoStorage(Map<String,Object> map);
	 int  updatealbumStorage(Map<String,Object> map);
	 int  updatealbumvideoStorage(Map<String,Object> map);

	    
	    //根据id查询是否已入库
	   int  inservodalbumid(Map<String, Object> queryParams);
	   int  updatevodalbumid(Map<String, Object> queryParams);
	   int  insertvideoid(Map<String, Object> queryParams);
	   int updatevideobycentralid(Map<String, Object> queryParams);
	   int updatevodmapping(Map<String, Object> queryParams);
	   int updateactors(Map<String, Object> queryParams);
	   int updatevodalbumstotageid(Map<String, Object> queryParams);
	   int updatevodalbumvideostotageid(Map<String, Object> queryParams);
}
