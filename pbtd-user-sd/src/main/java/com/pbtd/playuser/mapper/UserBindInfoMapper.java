package com.pbtd.playuser.mapper;

import com.pbtd.playuser.domain.UserBindInfo;

public interface UserBindInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserBindInfo record);

    int insertSelective(UserBindInfo record);

    UserBindInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBindInfo record);

    int updateByPrimaryKey(UserBindInfo record);
}