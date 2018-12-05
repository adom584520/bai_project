package com.pbtd.playuser.mapper;

import java.util.HashMap;

import com.pbtd.playuser.domain.UserActivitiesInfo;

public interface UserActivitiesInfoMapper {

	UserActivitiesInfo selectByPrimary(HashMap<String, Object> params);

	int insert(HashMap<String, Object> params);

	int updateByPrimary(HashMap<String, Object> params);
	
	
	int deleteByPrimaryKey(Integer id);


	int insertSelective(UserActivitiesInfo record);



	int update(HashMap<String, Object> params);
	int updateReset(HashMap<String, Object> params);


}