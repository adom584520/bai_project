package com.pbtd.manager.vod.phone.hotsearch.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.phone.album.domain.Vodalbuminfo;
import com.pbtd.manager.vod.phone.hotsearch.domain.VodHotSearchInfo;

public interface IVodHotSearchService {
	
	//总条数
	int count(Map<String, Object> queryParams);
	//分页查询
	List<Map<String,Object>> page(Map<String, Object> queryParams);
	//下发热搜接口查询
	List<Map<String,Object>> find(Map<String, Object> queryParams);
	//删除
	int  deletes(VodHotSearchInfo vodHostSearchInfo);
	//更新
	int update(VodHotSearchInfo vodHostSearchInfo);
	//添加
	int insert(VodHotSearchInfo vodHostSearchInfo);
	 //查询绑定信息排序
    List<Map<String, Object>> findalbumsequence(Map<String, Object> map);
    //查询绑定信息的最大和最小排序
    Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map);
    //查询更改数据的历史排序
    List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams);
}
