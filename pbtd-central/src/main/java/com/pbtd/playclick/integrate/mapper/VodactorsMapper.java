package com.pbtd.playclick.integrate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.integrate.domain.Vodactors;
@Mapper
public interface VodactorsMapper  {

	/**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

    /**
     * 模糊获取符合查询条件的分页记录
     * @return 记录列表
     */
    List<Vodactors> page( Map<String, Object> queryParams);

    /**
     * 精确获取符合查询条件的记录
     */
    List<Vodactors> find(Map<String, Object> queryParams);
    List<Vodactors> findlist(Map<String, Object> queryParams);
    /**
     * 精确生成将要插入的记录的序号
     */
    int generatePosition(Map<String, Object> queryParams);

    /**
     * 根据标识获取记录
     */
    Vodactors load(int id);

    /**
     * 插入记录
     */
    int insert(Vodactors vodactors);
    int insertselect(Map<String, Object> queryParams);

    /**
     * 修改记录
     */
    int update(Vodactors vodactors);

	/**
     * 删除多条
     */
    int deletes(Map<String, Object> ids);
 
}
