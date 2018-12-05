package com.pbtd.manager.vod.phone.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.vod.phone.common.domain.Vodlabel;

@Mapper
public interface VodLabelMapper {

	/**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

	/**
     * 模糊获取符合查询条件的分页记录
     * @return 记录列表
     */
    List<Vodlabel> page(int start, int limit, Map<String, Object> queryParams);

	/**
     * 精确获取符合查询条件的记录
     */
    List<Vodlabel> find(Map<String, Object> queryParams);

	/**
     * 精确生成将要插入的记录的序号
     */
    int generatePosition(Map<String, Object> queryParams);

	/**
     * 根据标识获取记录
     */
    Vodlabel load(int id);

	/**
     * 插入记录
     */
    int insert(Vodlabel vodlabel);
    int insertjson(Vodlabel vodlabel);

	/**
     * 修改记录
     */
    int update(Vodlabel vodlabel);
    /**
     * 批量上线下线
     * @param queryParams
     * @return
     */
    int updateStatus(Map<String,Object> map);

	int updatesequce(Map<String, Object> queryParams);

	int addsequce(Map<String, Object> queryParams);

	int deletesequce(Map<String, Object> queryParams);

	/**
     * 删除多条
     */
    int deletes(Map<String, Object> ids);

	int insertPrimary(@Param("id")Integer id, @Param("name")String name, @Param("channelCode")Integer channelCode, @Param("level")Integer level);
	  //添加绑定频道
	  int addlabelchannel(Map<String, Object>  map);
	//删除绑定频道
	  int deletelabelchannel(Map<String, Object> map);
	//分页查询绑定频道
	  int countlabelchannel(Map<String, Object> queryParams);
	//分页查询绑定频道
	  List<Map<String, Object>> pagelabelchannel(Map<String, Object> queryParams); 
	 int countchannel(Map<String,Object> map);

	  Map<String, Object>   findsequence(Map<String, Object> queryParams);
	  //查询排序的最大值和最小值
	  Map<String, Object> findmaxVSminsequence(Map<String, Object> map);
	 //查询更改数据的历史排序
	  List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams);
	  //查询排序
	  List<Map<String, Object>> findlabelsequence(Map<String, Object> map);
	  //获取类型
	  String getType(Map<String, Object> map);
	  int updateimg(Map<String,Object> Map);
}
