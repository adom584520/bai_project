package com.pbtd.manager.system.mapper;

import com.pbtd.manager.system.domain.UserKeep;

import java.util.List;
import java.util.Map;

public interface UserKeepMapper {
    public int insertData(Map<String, Object> insertParams);

    List<UserKeep> getUserKeep(Map<String, Object> queryParams);

    int isUserKeepExist(Map<String, Object> queryParams);
}
