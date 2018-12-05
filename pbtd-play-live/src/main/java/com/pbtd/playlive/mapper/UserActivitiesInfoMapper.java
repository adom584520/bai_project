package com.pbtd.playlive.mapper;

import com.pbtd.playlive.domain.UserActivitiesInfo;

public interface UserActivitiesInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserActivitiesInfo record);

    int insertSelective(UserActivitiesInfo record);

    UserActivitiesInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserActivitiesInfo record);

    int updateByPrimaryKey(UserActivitiesInfo record);
}