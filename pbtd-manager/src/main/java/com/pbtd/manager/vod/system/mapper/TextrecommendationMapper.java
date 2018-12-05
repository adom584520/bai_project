package com.pbtd.manager.vod.system.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.system.domain.Textrecommendation;
@Mapper
public interface TextrecommendationMapper {

    /**
     * 插入记录
     */
     public int insert(Textrecommendation map);
     
     /**
      *更新
      */
    int update(Textrecommendation map); 
    
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
     
     public List<Textrecommendation> page(  Map<String, Object> queryParams);
     /**
      * 精确获取符合查询条件的记录
      */
     public List<Textrecommendation> find(Map<String, Object> queryParams);
    

     /**
      * 根据标识获取记录
      */
     Textrecommendation load(int id);
         
     /**
      * 模糊统计符合查询条件的记录总数
      */
     int count(Map<String, Object> queryParams);
     //下发获取数据接口
     List<Textrecommendation>  findforinterface(  Map<String, Object> queryParams);
     int updateimg(Map<String,Object> Map);
}
