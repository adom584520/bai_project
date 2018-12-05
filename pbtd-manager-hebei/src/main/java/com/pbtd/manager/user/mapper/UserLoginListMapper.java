package com.pbtd.manager.user.mapper;

import java.util.Map;

import com.pbtd.manager.user.domain.UserLoginList;

public interface UserLoginListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLoginList record);

    int insertSelective(UserLoginList record);

    UserLoginList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLoginList record);

    int updateByPrimaryKey(UserLoginList record);
    
    Integer activecount(Map<String,Object> queryParams);
    
    Integer selectactiveuserkeepcount(Map<String,Object> queryParams);

    int getUserCount(Map<String,Object> queryParams);
}