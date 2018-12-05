package com.pbtd.manager.vod.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.system.domain.RecommandPic;

@Mapper
public interface RecommandPicMapper {

	/**
	 * 查询频道列表
	 * @param channel_id
	 * @return
	 */
     public List<Map<String,Object>> queryChannelInfo();

	public RecommandPic queryByChannel_id(String channel_id);

	public long count();

	/**
      * 插入记录
      */
     public int add(RecommandPic recommandPic);

	/**
      * 修改图片
      */
     public int updateImg(RecommandPic recomandPic);

	/**
      * 编辑
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
      * 精确获取符合查询条件的记录
      */
      public List<RecommandPic> find(Map<String, Object> queryParams);

	/**
      * 模糊获取符合查询条件的分页记录
      * @return 记录列表
      */
     public List<Map<String,Object>> showAll(Map<String, Object> queryParams);

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
