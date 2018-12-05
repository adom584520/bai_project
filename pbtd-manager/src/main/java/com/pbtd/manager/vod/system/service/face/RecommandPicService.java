package com.pbtd.manager.vod.system.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.phone.album.domain.Vodalbuminfo;
import com.pbtd.manager.vod.system.domain.RecommandPic;

public interface RecommandPicService {
	/**
	 * 查询频道列表
	 */
	public List<Map<String,Object>> queryChannelInfo();

	/**
     * 插入记录
     */
     public int add(RecommandPic recommandPic);

	/**
      * 修改记录
      */
     public int modify(RecommandPic recommandPic);

	/**
      * 批量上线下线
      */
    int updateStatus(Map<String,Object> map);

	/**
      * 删除多条
      */
    public int deletes(Map<String, Object> ids);

	/**
      * 模糊获取符合查询条件的分页记录
      * @return 记录列表
      */
     
     public List<Map<String,Object>> page(Map<String, Object> queryParams);

	/**
      * 精确获取符合查询条件的记录
      */
     public List<RecommandPic> find(Map<String, Object> queryParams);

	public void updateImg(RecommandPic recomandPic);

	/**
      * 精确生成将要插入的记录的序号
      */
     int generatePosition(Map<String, Object> queryParams);

	/**
      * 根据标识获取记录
      */
     RecommandPic load(int id);

	/**
      * 模糊统计符合查询条件的记录总数
      */
     int count(Map<String, Object> queryParams);

	// 查询排序的最大值和最小值
	Map<String, Object> findmaxVSminsequence(Map<String, Object> map);

	// 查询更改数据的历史排序
	List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams);

	// 查询排序
	List<Map<String, Object>> findsequence(Map<String, Object> map);

}
