package com.pbtd.playuser.mapper;

import java.util.HashMap;
import java.util.List;

import com.pbtd.playuser.domain.UserLiveCollectInfo;

public interface UserLiveCollectInfoMapper {
    int delete(HashMap<String,Object> params);

    int insert(HashMap<String,Object> params);

    
    List<UserLiveCollectInfo> select(HashMap<String,Object> params);

    
    
    
}