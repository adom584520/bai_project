package com.pbtd.playclick.yinhe.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pbtd.playclick.yinhe.domain.YhLabel;
@Mapper
public interface YhLabelMapper {
	
	List<YhLabel> find(Map<String, Object> queryParams);
	
	int insert(YhLabel label);
     
    int deleteByPrimaryKey(@Param("tagId")String tagId,@Param("chnId")String chnId);

    int insertSelective(YhLabel record);

    YhLabel selectByPrimaryKey(@Param("tagId")String tagId,@Param("chnId")String chnId);

    int updateByPrimaryKeySelective(YhLabel record);

    int updateByPrimaryKey(YhLabel record);
}
