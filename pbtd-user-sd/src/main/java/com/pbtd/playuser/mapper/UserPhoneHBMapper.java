package com.pbtd.playuser.mapper;

import java.util.HashMap;
import java.util.List;

import com.pbtd.playuser.domain.UserPhoneHB;

public interface UserPhoneHBMapper {
	
	
	List<UserPhoneHB> select(HashMap<?, ?> tempBean);
	
    int deleteByPrimaryKey(Integer id);

    int insert(UserPhoneHB record);

    int insertSelective(UserPhoneHB record);

    UserPhoneHB selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPhoneHB record);

    int updateByPrimaryKey(UserPhoneHB record);
}