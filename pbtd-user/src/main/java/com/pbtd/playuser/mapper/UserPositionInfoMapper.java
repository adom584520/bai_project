package com.pbtd.playuser.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pbtd.playuser.domain.UserPositionInfo;

public interface UserPositionInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPositionInfo record);

    int insertSelective(Map<String, Object> map);

    UserPositionInfo selectByPrimaryKey(Integer id);
    
    int select(Map<String, Object> params);

    int updateByPrimaryKeySelective(UserPositionInfo record);

    int updateByPrimaryKey(UserPositionInfo record);
    
    List<UserPositionInfo>  selectstatistics(Map<String,Object> params);
    List<UserPositionInfo>  selectstatisticsformodule(Map<String,Object> params);
    List<UserPositionInfo>  selectstatisticsforbiaoqian(Map<String,Object> params);
    List<UserPositionInfo>  selectstatisticsforshanxuanbiaoqian(Map<String,Object> params);
    List<UserPositionInfo>  selectstatisticsforcainixihuan(Map<String,Object> params);
}