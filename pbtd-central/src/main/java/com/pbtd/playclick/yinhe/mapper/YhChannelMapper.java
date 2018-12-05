package com.pbtd.playclick.yinhe.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.yinhe.domain.YhChannel;
@Mapper
public interface YhChannelMapper {
	
	 int count(Map<String, Object> queryParams);
	 
     List<YhChannel> page(int start, int limit, Map<String, Object> queryParams);
     
     List<YhChannel> find(Map<String, Object> queryParams);
     
     int insert(YhChannel vodChannel);
     
     int deleteByPrimaryKey(String chnid);

     int insertSelective(YhChannel record);

     YhChannel selectByPrimaryKey(String chnid);

     int updateByPrimaryKeySelective(YhChannel record);

     int updateByPrimaryKey(YhChannel record);
}
