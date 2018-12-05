package com.pbtd.manager.vod.system.service.face;



import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.system.domain.Textrecommendation;



public interface ITextrecommendationService {
	
    /**
     * 插入记录
     */
     public int insert(Textrecommendation map);
     
     /**
      *更新
      */
    int update(Textrecommendation m); 
    
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
   //保存下发数据
     int insertjson(Textrecommendation map);
	  int updateimg(Map<String,Object> Map);

}
