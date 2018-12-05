package com.pbtd.manager.vod.tv.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.vod.tv.common.domain.VodTvlabel;

@Mapper
public interface VodTvLabelMapper  {

	/**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

    /**
     * 模糊获取符合查询条件的分页记录
     * @return 记录列表
     */
    List<VodTvlabel> page(int start, int limit, Map<String, Object> queryParams);

    /**
     * 精确获取符合查询条件的记录
     */
    List<VodTvlabel> find(Map<String, Object> queryParams);
    
    /**
     * 精确生成将要插入的记录的序号
     */
    int generatePosition(Map<String, Object> queryParams);

    /**
     * 根据标识获取记录
     */
    VodTvlabel load(int id);

    /**
     * 插入记录
     */
    int insert(VodTvlabel vodlabel);
    int insertjson(VodTvlabel vodlabel);
    /**
     * 修改记录
     */
    int update(VodTvlabel vodlabel);
    /**
     * 批量上线下线
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
}
