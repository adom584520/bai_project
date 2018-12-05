package com.pbtd.manager.vod.phone.special.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.phone.special.domain.VodSpecial;
public interface IVodSpecialService  {

	/**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

    /**
     * 模糊获取符合查询条件的分页记录
     * @return 记录列表
     */
    List<VodSpecial> page(int start, int limit, Map<String, Object> queryParams);

    /**
     * 精确获取符合查询条件的记录
     */
    List<VodSpecial> find(Map<String, Object> queryParams);
    
    /**
     * 精确生成将要插入的记录的序号
     */
    int generatePosition(Map<String, Object> queryParams);

    /**
     * 根据标识获取记录
     */
    VodSpecial load(int id);

    /**
     * 插入记录
     */
    int insert(VodSpecial vodSpecial);
    int insertjson(VodSpecial vodSpecial);
    //插入绑定专辑信息
    int insertalbum(Map<String, Object> map);
    //更改绑定信息排序
    int updatealbumsequence(Map<String, Object> map);
    //删除绑定信息
    int  deletesalbum(Map<String, Object> map);
    //绑定信息查询
    int countalbum(Map<String, Object> queryParams);
    //绑定信息查询
    List<Map<String, Object>> pagealbum(Map<String, Object> queryParams);
    /**
     * 修改记录
     */
    int update(VodSpecial vodSpecial);
    int updateimg(VodSpecial vodSpecial);
     int updatezt(int id,int status);
  //  int updatezt(Map<String,Object> map);
	/**
     * 删除多条
     */
    int deletes(Map<String, Object> ids);
    //查询更改数据的历史排序
    List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams);
    //查询绑定信息排序
    List<Map<String, Object>> findalbumsequence(Map<String, Object> map);
    //查询绑定信息的最大和最小排序
    Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map);
  
    //专题排序 
    //查询排序的最大值和最小值
	  Map<String, Object> findmaxVSminsequence(Map<String, Object> map);
	 //查询更改数据的历史排序
	  List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams);
	  //查询排序
	  List<Map<String, Object>> findsequence(Map<String, Object> map);
 
}
