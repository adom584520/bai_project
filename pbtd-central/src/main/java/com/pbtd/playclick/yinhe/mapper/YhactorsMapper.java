package com.pbtd.playclick.yinhe.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pbtd.playclick.yinhe.domain.YhActors;
@Mapper
public interface YhactorsMapper {
	
	List<YhActors> find(Map<String, Object> queryParams);
	
	int insert(YhActors ators);
   
    int deleteByPrimaryKey(String code);
    
    int insertSelective(YhActors record);
    
    YhActors selectByPrimaryKey(String code);
    
    int updateByPrimaryKeySelective(YhActors record);
    
    int updateByPrimaryKey(YhActors record);
    
    int updateActor(@Param("actor")String actor,@Param("code")String code);
    
    int updateDirector(@Param("director")String director,@Param("code")String code);
}
