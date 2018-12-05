package com.pbtd.playuser.mapper;

import java.util.Map;

import com.pbtd.playuser.domain.UserPosition;

public interface UserPositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPosition record);

    int insertSelective(Map<String, Object> queryParams);

    UserPosition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPosition record);

    int updateByPrimaryKey(UserPosition record);
}