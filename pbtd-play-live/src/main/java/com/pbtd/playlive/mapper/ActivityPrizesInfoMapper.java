package com.pbtd.playlive.mapper;

import com.pbtd.playlive.domain.ActivityPrizesInfo;

public interface ActivityPrizesInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityPrizesInfo record);

    int insertSelective(ActivityPrizesInfo record);

    ActivityPrizesInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityPrizesInfo record);

    int updateByPrimaryKeyWithBLOBs(ActivityPrizesInfo record);

    int updateByPrimaryKey(ActivityPrizesInfo record);
}