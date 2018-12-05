package com.pbtd.manager.vod.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;



import com.pbtd.manager.vod.system.domain.BottomNavigation;

@Mapper
public interface BottomNavigationMapper {

     
     /**
      * 插入记录
      */
     public int add(BottomNavigation bottomNavigation);
     
     /**
      * 修改图片
      */
     public int updateImg(BottomNavigation bottomNavigation);
     /**
      * 修改图片
      */
     public int modify(BottomNavigation bottomNavigation);
     
 	/**
      * 删除多条
      */
     public int deletes(Map<String, Object> ids);
     
     /**
      * 精确获取符合查询条件的记录
      */
      public List<BottomNavigation> find(Map<String, Object> queryParams);
     /**
      * 模糊获取符合查询条件的分页记录
      * @return 记录列表
      */
     public List<BottomNavigation> showAll(int start, int limit,Map<String, Object> queryParams);

     
     /**
      * 精确生成将要插入的记录的序号
      */
     int generatePosition(Map<String, Object> queryParams);

     /**
      * 根据标识获取记录
      */
     BottomNavigation load(int id);
     

 	/**
      * 模糊统计符合查询条件的记录总数
      */
     int count(Map<String, Object> queryParams);
}
