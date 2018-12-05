package com.pbtd.manager.user.mapper;

import java.util.Map;

import com.pbtd.manager.user.domain.UserBaseInfo;

public interface UserBaseInfoMapper {
    int deleteByPrimaryKey(String userid);

    int insert(UserBaseInfo record);

    int insertSelective(UserBaseInfo record);

    UserBaseInfo selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(UserBaseInfo record);

    int updateByPrimaryKey(UserBaseInfo record);
    
    
    int usercount(Map<String,Object> queryParams);

    int getUserCount(Map<String, Object> queryParams);
}