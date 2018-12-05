package com.pbtd.playuser.mapper;

import java.util.HashMap;
import java.util.List;

import com.pbtd.playuser.domain.ActivitiesBaseInfo;

public interface ActivitiesBaseInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivitiesBaseInfo record);

    int insertSelective(ActivitiesBaseInfo record);

    List<ActivitiesBaseInfo> queryAll();
    ActivitiesBaseInfo queryOne(HashMap<String, Object> params);

    int updateByPrimaryKeySelective(ActivitiesBaseInfo record);

    int updateByPrimaryKey(ActivitiesBaseInfo record);
}