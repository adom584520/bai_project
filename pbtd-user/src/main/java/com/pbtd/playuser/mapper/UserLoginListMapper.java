package com.pbtd.playuser.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pbtd.playuser.domain.UserLoginList;

public interface UserLoginListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLoginList record);

    int insertSelective(UserLoginList record);

    UserLoginList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLoginList record);

    int updateByPrimaryKey(UserLoginList record);
    int count();
    int countindate(Map<String,Object> queryParams);
   // HashMap<String,String>  listcount(Map<String, Object> queryParams);
    List<Map<String,Object>> listcount(Map<String,Object> queryParams);
}