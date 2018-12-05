package com.pbtd.playuser.mapper;

import java.util.List;
import java.util.Map;

import com.pbtd.playuser.domain.UserSearchInfo;

public interface UserSearchInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserSearchInfo record);

    int insertSelective(Map<String, Object> queryParams);

    UserSearchInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserSearchInfo record);

    int updateByPrimaryKey(UserSearchInfo record);
    
    int selectforall(Map<String, Object> queryParams);
    List<UserSearchInfo> selectforsousuowenzi(Map<String, Object> queryParams);

}