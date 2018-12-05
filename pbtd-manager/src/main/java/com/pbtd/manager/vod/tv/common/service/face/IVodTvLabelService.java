package com.pbtd.manager.vod.tv.common.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.tv.common.domain.VodTvlabel;
public interface IVodTvLabelService  {

	/**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

    /**
     * 模糊获取符合查询条件的分页记录
     * @return 记录列表
     */
    List<VodTvlabel> page(Map<String, Object> queryParams);

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

    /**
     * 修改记录
     */
    int update(VodTvlabel vodlabel);
    /**
     * 批量上线下线
     */
   int updateStatus(Map<String,Object> map);
	/**
     * 删除多条
     */
    int deletes(Map<String, Object> map,List<Integer> ids);
 
 
}
